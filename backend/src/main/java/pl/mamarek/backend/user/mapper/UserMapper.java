package pl.mamarek.backend.user.mapper;

import org.mapstruct.Mapper;
import pl.mamarek.backend.user.dto.UserDto;
import pl.mamarek.backend.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
}
