package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.utils.MapperUtils.allocationMapper;
import static br.com.sw2you.realmeet.utils.TestDataCreator.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.mapper.AllocationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AllocationMapperUnitTest extends BaseUnitTest {
    private AllocationMapper victim;

    @BeforeEach
    void setupEach() {
        victim = allocationMapper();
    }

    @Test
    void testFromCreateAllocationDTOToEntity() {
        var createAllocationDTO = newCreateAllocationDTO();
        var room = newRoomBuilder().build();
        var allocation = victim.fromCreateAllocationDTOToEntity(createAllocationDTO, room);

        assertEquals(createAllocationDTO.getSubject(), allocation.getSubject());
        assertNull(allocation.getRoom().getId());
        assertEquals(createAllocationDTO.getEmployeeName(), allocation.getEmployee().getName());
        assertEquals(createAllocationDTO.getEmployeeEmail(), allocation.getEmployee().getEmail());
        assertEquals(createAllocationDTO.getStartAt(), allocation.getStartAt());
        assertEquals(createAllocationDTO.getEndAt(), allocation.getEndAt());
    }

    @Test
    void testFromEntityToAllocationDTO() {
        var room = newRoomBuilder().withId(1L).build();
        var allocation = newAllocationBuilder(room).build();
        var allocationDTO = victim.fromEntityToAllocationDTO(allocation);

        assertEquals(allocation.getSubject(), allocationDTO.getSubject());
        assertEquals(allocation.getId(), allocationDTO.getId());
        assertEquals(allocation.getRoom().getId(), allocationDTO.getRoomId());
        assertNotNull(allocationDTO.getRoomId());
        assertEquals(allocation.getEmployee().getName(), allocationDTO.getEmployeeName());
        assertEquals(allocation.getEmployee().getEmail(), allocationDTO.getEmployeeEmail());
        assertEquals(allocation.getStartAt(), allocationDTO.getStartAt());
        assertEquals(allocation.getEndAt(), allocationDTO.getEndAt());
    }
}
