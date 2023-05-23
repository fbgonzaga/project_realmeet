package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.util.DateUtils.*;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.api.model.UpdateAllocationDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import java.time.Duration;
import java.time.OffsetDateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class AllocationValidator {
    private final AllocationRepository allocationRepository;

    public AllocationValidator(AllocationRepository allocationRepository) {
        this.allocationRepository = allocationRepository;
    }

    public void validate(CreateAllocationDTO createAllocationDTO) {
        var validationErrors = new ValidationErrors();

        validateSubject(createAllocationDTO.getSubject(), validationErrors);
        validateEmployeeName(createAllocationDTO.getEmployeeName(), validationErrors);
        validateEmployeeEmail(createAllocationDTO.getEmployeeEmail(), validationErrors);
        validateDates(
            createAllocationDTO.getRoomId(),
            createAllocationDTO.getStartAt(),
            createAllocationDTO.getEndAt(),
            validationErrors
        );

        throwOnError(validationErrors);
    }

    public void validate(Long allocationId, Long roomId, UpdateAllocationDTO updateAllocationDTO) {
        var validationErrors = new ValidationErrors();

        validateRequired(allocationId, ALLOCATION_ID, validationErrors);
        validateSubject(updateAllocationDTO.getSubject(), validationErrors);
        validateDates(roomId, updateAllocationDTO.getStartAt(), updateAllocationDTO.getEndAt(), validationErrors);

        throwOnError(validationErrors);
    }

    private void validateSubject(String subject, ValidationErrors validationErrors) {
        validateRequired(subject, ALLOCATION_SUBJECT, validationErrors);
        validateMaxLength(subject, ALLOCATION_SUBJECT, ALLOCATION_SUBJECT_MAX_LENGTH, validationErrors);
    }

    private void validateEmployeeName(String employeeName, ValidationErrors validationErrors) {
        validateRequired(employeeName, ALLOCATION_EMPLOYEE_NAME, validationErrors);
        validateMaxLength(
            employeeName,
            ALLOCATION_EMPLOYEE_NAME,
            ALLOCATION_EMPLOYEE_NAME_MAX_LENGTH,
            validationErrors
        );
    }

    private void validateEmployeeEmail(String employeeEmail, ValidationErrors validationErrors) {
        validateRequired(employeeEmail, ALLOCATION_EMPLOYEE_EMAIL, validationErrors);
        validateMaxLength(
            employeeEmail,
            ALLOCATION_EMPLOYEE_EMAIL,
            ALLOCATION_EMPLOYEE_EMAIL_MAX_LENGTH,
            validationErrors
        );
    }

    private void validateDates(
        Long roomId,
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        ValidationErrors validationErrors
    ) {
        if (validateDatesPresent(startAt, endAt, validationErrors)) {
            validateDateOrdering(startAt, endAt, validationErrors);
            validateDateInTheFuture(startAt, validationErrors);
            validateDuration(startAt, endAt, validationErrors);
            validateIfTimeAvailable(roomId, startAt, endAt, validationErrors);
        }
    }

    private boolean validateDatesPresent(
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        ValidationErrors validationErrors
    ) {
        return (
            validateRequired(startAt, ALLOCATION_START_AT, validationErrors) &&
            validateRequired(endAt, ALLOCATION_END_AT, validationErrors)
        );
    }

    private void validateDateOrdering(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErrors validationErrors) {
        if (startAt.isEqual(endAt) || startAt.isAfter(endAt)) {
            validationErrors.add(ALLOCATION_START_AT, ALLOCATION_START_AT + INCONSISTENT);
        }
    }

    private void validateDateInTheFuture(OffsetDateTime date, ValidationErrors validationErrors) {
        if (date.isBefore(now())) {
            validationErrors.add(ALLOCATION_START_AT, ALLOCATION_START_AT + IN_THE_PAST);
        }
    }

    private void validateDuration(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErrors validationErrors) {
        if (Duration.between(startAt, endAt).getSeconds() > ALLOCATION_MAX_DURATION_SECONDS) {
            validationErrors.add(ALLOCATION_END_AT, ALLOCATION_END_AT + EXCEEDS_DURATION);
        }
    }

    private void validateIfTimeAvailable(
        Long roomId,
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        ValidationErrors validationErrors
    ) {
        allocationRepository
            .findAllWithFilters(null, roomId, now(), endAt)
            .stream()
            .filter(a -> DateUtils.isOverlapping(startAt, endAt, a.getStartAt(), a.getEndAt()))
            .findFirst()
            .ifPresent(__ -> validationErrors.add(ALLOCATION_START_AT, ALLOCATION_START_AT + OVERLAPS));
    }
}
