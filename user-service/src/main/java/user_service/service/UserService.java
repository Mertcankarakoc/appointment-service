package user_service.service;

import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import user_service.dto.UserDto;
import user_service.exception.NotOkResponseException;
import user_service.mapper.UserMapper;
import user_service.model.User;
import user_service.properties.ResponseMessageProperties;
import user_service.properties.ResponseStatusProperties;
import user_service.repository.UserRepository;
import user_service.request.CreateUserRequest;
import user_service.response.CreateUserRes;
import user_service.response.GetUsersRes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CreateUserRes createUser(CreateUserRequest createUserRequest) {
        log.trace("Create user with request: {}", createUserRequest);

        if (createUserRequest == null || createUserRequest.getUser() == null) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MESSAGE_003, 400, null);
        }

        UserDto userDto = createUserRequest.getUser();
        List<User> users = userRepository.findAllByEmail(userDto.getEmail());

        if (!users.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MESSAGE_005, 400, null);
        }

        User user = userMapper.toUser(userDto);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return CreateUserRes.builder()
                .message(ResponseMessageProperties.MESSAGE_004)
                .status(ResponseStatusProperties.CREATED)
                .statusCode(201)
                .build();
    }

    public GetUsersRes getUsers() {
        log.trace("Get users");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            throw new NotOkResponseException(HttpStatus.NOT_FOUND, ResponseMessageProperties.MESSAGE_002, 404, null);
        }

        List<UserDto> userDtoList = userMapper.toUserDtoList(users);
        return GetUsersRes.builder()
                .data(userDtoList)
                .message(ResponseMessageProperties.MESSAGE_001)
                .status(ResponseStatusProperties.SUCCESS)
                .statusCode(200)
                .build();

    }

    public GetUsersRes getUserById(Long userId) {
        log.trace("Get user by id {}", userId);

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.NOT_FOUND, "User not found", 404, null);
        }

        UserDto userDto = userMapper.toUserDto(user.get());
        return GetUsersRes.builder()
                .data(userDto)
                .message(ResponseMessageProperties.MESSAGE_001)
                .status(ResponseStatusProperties.SUCCESS)
                .statusCode(200)
                .build();
    }

    public GetUsersRes getUserByName(String name) {
        log.trace("Get user by name {}", name);

        List<User> users = userRepository.findByName(name);
        if (users.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.NOT_FOUND, ResponseMessageProperties.MESSAGE_002, 404, null);
        }

        List<UserDto> userDto = userMapper.toUserDtoList(users);
        return GetUsersRes.builder()
                .data(userDto)
                .message(ResponseMessageProperties.MESSAGE_001)
                .status(ResponseStatusProperties.SUCCESS)
                .statusCode(200)
                .build();
    }

    public GetUsersRes getUserBySurname(String surname) {
        log.trace("Get user by surname {}", surname);

        List<User> users = userRepository.findBySurname(surname);
        if (users.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.NOT_FOUND, ResponseMessageProperties.MESSAGE_002, 404, null);
        }

        List<UserDto> userDto = userMapper.toUserDtoList(users);
        return GetUsersRes.builder()
                .data(userDto)
                .message(ResponseMessageProperties.MESSAGE_001)
                .status(ResponseStatusProperties.SUCCESS)
                .statusCode(200)
                .build();
    }

    public GetUsersRes getUserByNameAndSurname(String name, String surname) {
        log.trace("Get user by name and surname {}", surname);

        List<User> users = userRepository.findByNameAndSurname(name, surname);
        if (users.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.NOT_FOUND, ResponseMessageProperties.MESSAGE_002, 404, null);
        }

        List<UserDto> userDto = userMapper.toUserDtoList(users);
        return GetUsersRes.builder()
                .data(userDto)
                .message(ResponseMessageProperties.MESSAGE_001)
                .status(ResponseStatusProperties.SUCCESS)
                .statusCode(200)
                .build();
    }
}
