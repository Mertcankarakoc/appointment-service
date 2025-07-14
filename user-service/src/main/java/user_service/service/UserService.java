package user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user_service.dto.UserDto;
import user_service.exception.NotOkResponseException;
import user_service.mapper.UserMapper;
import user_service.model.Role;
import user_service.model.User;
import user_service.properties.ResponseMessageProperties;
import user_service.properties.ResponseStatusProperties;
import user_service.repository.UserRepository;
import user_service.request.CreateUserReq;
import user_service.request.RegisterReq;
import user_service.request.UpdateUserReq;
import user_service.response.CreateUserRes;
import user_service.response.GetUsersRes;
import user_service.response.RegisterRes;
import user_service.response.UpdateUserRes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public CreateUserRes createUser(CreateUserReq createUserReq) {
        log.trace("Create user with request: {}", createUserReq);

        if (createUserReq == null || createUserReq.getUser() == null) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MSG_REQUEST_BODY_NULL, 400, null);
        }

        UserDto userDto = createUserReq.getUser();

        List<User> users = userRepository.findAllByEmail(userDto.getEmail());
        if (!users.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MSG_USER_ALREADY_EXISTS, 400, null);
        }

        Optional<User> existingUserByTckn = userRepository.findByTckn(userDto.getTckn());
        if (existingUserByTckn.isPresent()) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MSG_TCKN_ALREADY_EXISTS, 400, null);
        }

        User user = userMapper.toUser(userDto);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return CreateUserRes.builder()
                .message(ResponseMessageProperties.MSG_USER_CREATED)
                .status(ResponseStatusProperties.CREATED)
                .statusCode(201)
                .build();
    }

    public GetUsersRes getUsers() {
        log.trace("Get users");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.NOT_FOUND, ResponseMessageProperties.MSG_USERS_NOT_FOUND, 404, null);
        }

        List<UserDto> userDtoList = userMapper.toUserDtoList(users);
        return GetUsersRes.builder()
                .data(userDtoList)
                .message(ResponseMessageProperties.USERS_RETRIEVED_SUCCESSFULLY)
                .status(ResponseStatusProperties.SUCCESS)
                .statusCode(200)
                .build();
    }

    public GetUsersRes getUserById(Long userId) {
        log.trace("Get user by id {}", userId);

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.NOT_FOUND, ResponseMessageProperties.MSG_USER_NOT_FOUND, 404, null);
        }

        UserDto userDto = userMapper.toUserDto(user.get());
        return GetUsersRes.builder()
                .data(userDto)
                .message(ResponseMessageProperties.USERS_RETRIEVED_SUCCESSFULLY)
                .status(ResponseStatusProperties.SUCCESS)
                .statusCode(200)
                .build();
    }

    public GetUsersRes getUserByName(String name) {
        log.trace("Get user by name {}", name);

        List<User> users = userRepository.findByName(name);
        if (users.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.NOT_FOUND, ResponseMessageProperties.MSG_USERS_NOT_FOUND, 404, null);
        }

        List<UserDto> userDto = userMapper.toUserDtoList(users);
        return GetUsersRes.builder()
                .data(userDto)
                .message(ResponseMessageProperties.USERS_RETRIEVED_SUCCESSFULLY)
                .status(ResponseStatusProperties.SUCCESS)
                .statusCode(200)
                .build();
    }

    public GetUsersRes getUserBySurname(String surname) {
        log.trace("Get user by surname {}", surname);

        List<User> users = userRepository.findBySurname(surname);
        if (users.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.NOT_FOUND, ResponseMessageProperties.MSG_USERS_NOT_FOUND, 404, null);
        }

        List<UserDto> userDto = userMapper.toUserDtoList(users);
        return GetUsersRes.builder()
                .data(userDto)
                .message(ResponseMessageProperties.USERS_RETRIEVED_SUCCESSFULLY)
                .status(ResponseStatusProperties.SUCCESS)
                .statusCode(200)
                .build();
    }

    public GetUsersRes getUserByNameAndSurname(String name, String surname) {
        log.trace("Get user by name and surname {}", surname);

        List<User> users = userRepository.findByNameAndSurname(name, surname);
        if (users.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.NOT_FOUND, ResponseMessageProperties.MSG_USERS_NOT_FOUND, 404, null);
        }

        List<UserDto> userDto = userMapper.toUserDtoList(users);
        return GetUsersRes.builder()
                .data(userDto)
                .message(ResponseMessageProperties.USERS_RETRIEVED_SUCCESSFULLY)
                .status(ResponseStatusProperties.SUCCESS)
                .statusCode(200)
                .build();
    }

    public UpdateUserRes updateUser(Long userId, UpdateUserReq updateUserReq) {
        log.trace("Update user with id: {} and request: {}", userId, updateUserReq);

        if (updateUserReq == null || updateUserReq.getUser() == null) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MSG_REQUEST_BODY_NULL, 400, null);
        }

        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.NOT_FOUND, ResponseMessageProperties.MSG_USER_NOT_FOUND, 404, null);
        }

        UserDto userDto = updateUserReq.getUser();
        User user = existingUser.get();

        if (userDto.getTckn() != null && !userDto.getTckn().equals(user.getTckn())) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MSG_TCKN_CANNOT_BE_CHANGED, 400, null);
        }

        if (userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail())) {
            List<User> usersWithEmail = userRepository.findAllByEmail(userDto.getEmail());
            boolean emailUsedByAnother = usersWithEmail.stream().anyMatch(u -> !u.getId().equals(user.getId()));
            if (emailUsedByAnother) {
                throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MSG_EMAIL_ALREADY_EXISTS, 400, null);
            }
        }

        userMapper.updateUserFromDto(user, userDto);

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return UpdateUserRes.builder()
                .message(ResponseMessageProperties.MSG_USER_UPDATED)
                .status(ResponseStatusProperties.SUCCESS)
                .statusCode(200)
                .build();
    }

    public void deleteUser(Long userId) {
        log.trace("Delete user with id: {}", userId);

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotOkResponseException(HttpStatus.NOT_FOUND, ResponseMessageProperties.MSG_USER_NOT_FOUND, 404, null);
        }

        userRepository.deleteById(userId);
    }
}
