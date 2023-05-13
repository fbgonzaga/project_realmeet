package br.com.sw2you.realmeet.mapper;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.domain.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomDTO fromEntityToRoomDTO(Room room);

    @Mapping(source = "name", target = "withName")
    @Mapping(source = "seats", target = "withSeats")
    Room fromCreateRoomDTOToEntity(CreateRoomDTO createRoomDTO);
}
