package user_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import user_service.dto.UserDto;
import user_service.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserDto userDto);

    List<UserDto> toUserDtoList(List<User> users);

    UserDto toUserDto(User user);

    default void updateUserFromDto(User user, UserDto userDto) {
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getSurname() != null) {
            user.setSurname(userDto.getSurname());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getBirthDate() != null) {
            user.setBirthDate(userDto.getBirthDate());
        }
        if (userDto.getGender() != null) {
            user.setGender(userDto.getGender());
        }
        if (userDto.getAddress() != null) {
            user.setAddress(userDto.getAddress());
        }
        if (userDto.getBloodType() != null) {
            user.setBloodType(userDto.getBloodType());
        }
        if (userDto.getRole() != null) {
            user.setRole(userDto.getRole());
        }
        if (userDto.getIsActive() != null) {
            user.setIsActive(userDto.getIsActive());
        }
    }
}
