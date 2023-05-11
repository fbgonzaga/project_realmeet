package br.com.sw2you.realmeet.mapper;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.domain.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomDTO fromEntityToDto(Room room);

    @Mapping(target = "withName", source = "name")
    @Mapping(target = "withSeats", source = "seats")
    Room fromCreateRoomDtoToEntity(CreateRoomDTO createRoomDTO);
}
