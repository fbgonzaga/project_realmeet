package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.util.DateUtils.now;
import static br.com.sw2you.realmeet.utils.TestConstants.*;
import static br.com.sw2you.realmeet.utils.TestDataCreator.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.sw2you.realmeet.api.facade.AllocationApi;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.service.AllocationService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

class AllocationApiFilterIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private AllocationApi api;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    AllocationRepository allocationRepository;

    @Autowired
    private AllocationService allocationService;

    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(api.getApiClient(), "/v1");
    }

    @Test
    void testFilterAllAllocations() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var allocation1 = allocationRepository.saveAndFlush(
            newAllocationBuilder(room).withSubject(DEFAULT_ALLOCATION_SUBJECT + 1).build()
        );
        var allocation2 = allocationRepository.saveAndFlush(
            newAllocationBuilder(room).withSubject(DEFAULT_ALLOCATION_SUBJECT + 2).build()
        );
        var allocation3 = allocationRepository.saveAndFlush(
            newAllocationBuilder(room).withSubject(DEFAULT_ALLOCATION_SUBJECT + 3).build()
        );

        var allocationDTOList = api.listAllocations(TEST_CLIENT_API_KEY, null, null, null, null, null, null, null);

        assertEquals(3, allocationDTOList.size());
        assertEquals(allocation1.getSubject(), allocationDTOList.get(0).getSubject());
        assertEquals(allocation2.getSubject(), allocationDTOList.get(1).getSubject());
        assertEquals(allocation3.getSubject(), allocationDTOList.get(2).getSubject());
    }

    @Test
    void testFilterAllocationsByRoomId() {
        var roomA = roomRepository.saveAndFlush(newRoomBuilder().withName(DEFAULT_ROOM_NAME + "A").build());
        var roomB = roomRepository.saveAndFlush(newRoomBuilder().withName(DEFAULT_ROOM_NAME + "B").build());
        var allocation1 = allocationRepository.saveAndFlush(
            newAllocationBuilder(roomA).withSubject(DEFAULT_ALLOCATION_SUBJECT + 1).build()
        );
        var allocation2 = allocationRepository.saveAndFlush(
            newAllocationBuilder(roomA).withSubject(DEFAULT_ALLOCATION_SUBJECT + 2).build()
        );
        allocationRepository.saveAndFlush(
            newAllocationBuilder(roomB).withSubject(DEFAULT_ALLOCATION_SUBJECT + 3).build()
        );

        var allocationDTOList = api.listAllocations(
            TEST_CLIENT_API_KEY,
            null,
            roomA.getId(),
            null,
            null,
            null,
            null,
            null
        );

        assertEquals(2, allocationDTOList.size());
        assertEquals(allocation1.getId(), allocationDTOList.get(0).getId());
        assertEquals(allocation2.getId(), allocationDTOList.get(1).getId());
    }

    @Test
    void testFilterAllocationsByEmployeeEmail() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().withName(DEFAULT_ROOM_NAME).build());
        var employee1 = newEmployeeBuilder().withEmail(DEFAULT_EMPLOYEE_EMAIL + 1).build();
        var employee2 = newEmployeeBuilder().withEmail(DEFAULT_EMPLOYEE_EMAIL + 2).build();

        var allocation1 = allocationRepository.saveAndFlush(newAllocationBuilder(room).withEmployee(employee1).build());
        var allocation2 = allocationRepository.saveAndFlush(newAllocationBuilder(room).withEmployee(employee1).build());
        allocationRepository.saveAndFlush(newAllocationBuilder(room).withEmployee(employee2).build());

        var allocationDTOList = api.listAllocations(
            TEST_CLIENT_API_KEY,
            employee1.getEmail(),
            null,
            null,
            null,
            null,
            null,
            null
        );

        assertEquals(2, allocationDTOList.size());
        assertEquals(allocation1.getId(), allocationDTOList.get(0).getId());
        assertEquals(allocation2.getId(), allocationDTOList.get(1).getId());
    }

    @Test
    void testFilterAllocationsByDateRange() {
        var baseStartAt = now().plusDays(2).withHour(14).withMinute(0);
        var baseEndAt = now().plusDays(4).withHour(20).withMinute(0);

        var room = roomRepository.saveAndFlush(newRoomBuilder().build());

        var allocation1 = allocationRepository.saveAndFlush(
            newAllocationBuilder(room).withStartAt(baseStartAt.plusHours(1)).withEndAt(baseStartAt.plusHours(2)).build()
        );
        var allocation2 = allocationRepository.saveAndFlush(
            newAllocationBuilder(room).withStartAt(baseStartAt.plusHours(4)).withEndAt(baseStartAt.plusHours(5)).build()
        );
        allocationRepository.saveAndFlush(
            newAllocationBuilder(room)
                .withStartAt(baseEndAt.plusDays(1))
                .withEndAt(baseEndAt.plusDays(1).plusHours(1))
                .build()
        );

        var allocationDTOList = api.listAllocations(
            TEST_CLIENT_API_KEY,
            null,
            null,
            baseStartAt.toLocalDate(),
            baseEndAt.toLocalDate(),
            null,
            null,
            null
        );

        assertEquals(2, allocationDTOList.size());
        assertEquals(allocation1.getId(), allocationDTOList.get(0).getId());
        assertEquals(allocation2.getId(), allocationDTOList.get(1).getId());
    }

    @Test
    void testFilterAllocationUsingPagination() {
        persistAllocations(15);
        ReflectionTestUtils.setField(allocationService, "maxLimit", 10);

        var allocationListPage1 = api.listAllocations(TEST_CLIENT_API_KEY, null, null, null, null, null, null, 0);
        assertEquals(10, allocationListPage1.size());

        var allocationListPage2 = api.listAllocations(TEST_CLIENT_API_KEY, null, null, null, null, null, null, 1);
        assertEquals(5, allocationListPage2.size());
    }

    private void persistAllocations(int numberOfAllocations) {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());

        IntStream
            .range(0, numberOfAllocations)
            .forEach(
                i ->
                    allocationRepository.saveAndFlush(
                        newAllocationBuilder(room)
                            .withSubject(DEFAULT_ALLOCATION_SUBJECT + "_" + (i + 1))
                            .withStartAt(DEFAULT_ALLOCATION_START_AT.plusHours(i + 1))
                            .withEndAt(DEFAULT_ALLOCATION_END_AT.plusHours(i + 1))
                            .build()
                    )
            );
    }
}
