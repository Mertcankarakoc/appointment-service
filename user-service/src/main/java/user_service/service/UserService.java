package user_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import user_service.dto.UserDto;
import user_service.exception.GlobalExceptionHandler;
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

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CreateUserRes createUser(CreateUserRequest createUserRequest) {
        log.trace("Create user with request: {}", createUserRequest);

        if (createUserRequest == null || createUserRequest.getUser() == null) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MESSAGE_003, 400, null);
        }

        UserDto userDto = createUserRequest.getUser();

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return null;
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
}
