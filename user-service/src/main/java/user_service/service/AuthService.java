package user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user_service.exception.NotOkResponseException;
import user_service.model.Role;
import user_service.model.User;
import user_service.properties.ResponseMessageProperties;
import user_service.properties.ResponseStatusProperties;
import user_service.repository.UserRepository;
import user_service.request.LoginReq;
import user_service.request.RegisterReq;
import user_service.response.LoginRes;
import user_service.response.RegisterRes;
import user_service.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public RegisterRes register(RegisterReq registerReq) {
        log.trace("Register request: {}", registerReq);

        if (registerReq == null) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MSG_REQUEST_BODY_NULL, 400, null);
        }

        List<User> users = userRepository.findAllByEmail(registerReq.getEmail());
        if (!users.isEmpty()) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MSG_USER_ALREADY_EXISTS, 400, null);
        }

        User user = new User();
        user.setName(registerReq.getName());
        user.setSurname(registerReq.getSurname());
        user.setEmail(registerReq.getEmail());
        user.setPassword(passwordEncoder.encode(registerReq.getPassword()));
        user.setRole(registerReq.getRole() != null ? registerReq.getRole() : Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return RegisterRes.builder()
                .message(ResponseMessageProperties.MSG_USER_CREATED)
                .status(ResponseStatusProperties.CREATED)
                .statusCode(201)
                .build();
    }

    public LoginRes login(LoginReq loginReq) {
        log.trace("login request: {}", loginReq);

        if (loginReq == null) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MSG_REQUEST_BODY_NULL, 400, null);
        }

        User existingUser = userRepository.findByEmail(loginReq.getEmail());
        if (existingUser == null) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MSG_USER_NOT_FOUND, 400, null);
        }

        if (!passwordEncoder.matches(loginReq.getPassword(), existingUser.getPassword())) {
            throw new NotOkResponseException(HttpStatus.BAD_REQUEST, ResponseMessageProperties.MSG_PASSWORD_DIDNT_MATCH,  400, null);
        }

        User user = new User();
        user.setEmail(loginReq.getEmail());
        user.setPassword(passwordEncoder.encode(loginReq.getPassword()));

        String token = jwtUtil.createToken(user);

        return LoginRes.builder()
                .token(token)
                .status(ResponseStatusProperties.SUCCESS)
                .message(ResponseMessageProperties.MSG_LOGIN_SUCCESS)
                .statusCode(200)
                .build();
    }
}
