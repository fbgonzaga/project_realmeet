package br.com.sw2you.realmeet.mapper;

import br.com.sw2you.realmeet.api.model.AllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AllocationMapper {
    @Mapping(source = "room", target = "withRoom")
    @Mapping(source = "createAllocationDTO.employeeName", target = "withEmployee.withName")
    @Mapping(source = "createAllocationDTO.employeeEmail", target = "withEmployee.withEmail")
    @Mapping(source = "createAllocationDTO.subject", target = "withSubject")
    @Mapping(source = "createAllocationDTO.startAt", target = "withStartAt")
    @Mapping(source = "createAllocationDTO.endAt", target = "withEndAt")
    Allocation fromCreateAllocationDTOToEntity(CreateAllocationDTO createAllocationDTO, Room room);

    @Mapping(source = "employee.name", target = "employeeName")
    @Mapping(source = "employee.email", target = "employeeEmail")
    AllocationDTO fromEntityToAllocationDTO(Allocation allocation);
}
