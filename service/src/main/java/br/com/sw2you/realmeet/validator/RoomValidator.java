package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;
import static java.util.Objects.isNull;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {
    private final RoomRepository roomRepository;

    public RoomValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void validate(CreateRoomDTO createRoomDTO) {
        var validationErrors = new ValidationErrors();
        if (
            validateName(createRoomDTO.getName(), validationErrors) &&
            validateSeats(createRoomDTO.getSeats(), validationErrors)
        ) {
            validateNameDuplicate(null, createRoomDTO.getName(), validationErrors);
        }

        throwOnError(validationErrors);
    }

    public void validate(Long roomId, UpdateRoomDTO updateRoomDTO) {
        var validationErrors = new ValidationErrors();
        if (
            validateRequired(roomId, ROOM_ID, validationErrors) &&
            validateName(updateRoomDTO.getName(), validationErrors) &&
            validateSeats(updateRoomDTO.getSeats(), validationErrors)
        ) {
            validateNameDuplicate(roomId, updateRoomDTO.getName(), validationErrors);
        }

        throwOnError(validationErrors);
    }

    private boolean validateName(String name, ValidationErrors validationErrors) {
        return (
            validateRequired(name, ROOM_NAME, validationErrors) &&
            validateMaxLength(name, ROOM_NAME, ROOM_NAME_MAX_LENGTH, validationErrors)
        );
    }

    private boolean validateSeats(Integer seats, ValidationErrors validationErrors) {
        return (
            validateRequired(seats, ROOM_SEATS, validationErrors) &&
            validateMinValue(seats, ROOM_SEATS, ROOM_SEATS_MIN_VALUE, validationErrors) &&
            validateMaxValue(seats, ROOM_SEATS, ROOM_SEATS_MAX_VALUE, validationErrors)
        );
    }

    private void validateNameDuplicate(Long roomIdToExclude, String name, ValidationErrors validationErrors) {
        roomRepository
            .findByNameAndActive(name, true)
            .ifPresent(
                room -> {
                    if (isNull(roomIdToExclude) || !Objects.equals(room.getId(), roomIdToExclude)) {
                        validationErrors.add(ROOM_NAME, ROOM_NAME + DUPLICATE);
                    }
                }
            );
    }
}
