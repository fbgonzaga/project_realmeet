package br.com.sw2you.realmeet.utils;

import static br.com.sw2you.realmeet.utils.TestConstants.*;

import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateAllocationDTO;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.domain.model.Employee;

public final class TestDataCreator {

    private TestDataCreator() {}

    public static Room.Builder newRoomBuilder() {
        return Room.newBuilder().withName(DEFAULT_ROOM_NAME).withSeats(DEFAULT_ROOM_SEATS);
    }

    public static Allocation.Builder newAllocationBuilder(Room room) {
        return Allocation
            .newBuilder()
            .withSubject(DEFAULT_ALLOCATION_SUBJECT)
            .withRoom(room)
            .withEmployee(
                Employee.newBuilder().withName(DEFAULT_EMPLOYEE_NAME).withEmail(DEFAULT_EMPLOYEE_EMAIL).build()
            )
            .withStartAt(DEFAULT_ALLOCATION_START_AT)
            .withEndAt(DEFAULT_ALLOCATION_END_AT);
    }

    public static CreateRoomDTO newCreateRoomDTO() {
        return (CreateRoomDTO) new CreateRoomDTO().name(DEFAULT_ROOM_NAME).seats(DEFAULT_ROOM_SEATS);
    }

    public static CreateAllocationDTO newCreateAllocationDTO() {
        return new CreateAllocationDTO()
            .subject(DEFAULT_ALLOCATION_SUBJECT)
            .roomId(DEFAULT_ROOM_ID)
            .employeeName(DEFAULT_EMPLOYEE_NAME)
            .employeeEmail(DEFAULT_EMPLOYEE_EMAIL)
            .startAt(DEFAULT_ALLOCATION_START_AT)
            .endAt(DEFAULT_ALLOCATION_END_AT);
    }

    public static UpdateAllocationDTO newUpdateAllocationDTO() {
        return new UpdateAllocationDTO()
            .subject(DEFAULT_ALLOCATION_SUBJECT)
            .startAt(DEFAULT_ALLOCATION_START_AT)
            .endAt(DEFAULT_ALLOCATION_END_AT);
    }
}
