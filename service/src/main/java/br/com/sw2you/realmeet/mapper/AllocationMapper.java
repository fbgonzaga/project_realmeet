package br.com.sw2you.realmeet.mapper;

import br.com.sw2you.realmeet.api.model.AllocationDTO;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AllocationMapper {
    AllocationDTO fromEntityToDto(Allocation allocation);
}
