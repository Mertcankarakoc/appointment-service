package user_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import user_service.dto.UserDto;
import user_service.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toUser(UserDto userDto);

    List<UserDto> toUserDtoList(List<User> users);

    UserDto toUserDto(User user);
}
