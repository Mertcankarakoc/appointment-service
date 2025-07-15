package user_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user_service.request.LoginReq;
import user_service.request.RegisterReq;
import user_service.response.LoginRes;
import user_service.response.RegisterRes;
import user_service.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public RegisterRes register(@RequestBody RegisterReq registerReq) {
        return authService.register(registerReq);
    }

    @PostMapping("/login")
    public LoginRes login(@RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }
}
