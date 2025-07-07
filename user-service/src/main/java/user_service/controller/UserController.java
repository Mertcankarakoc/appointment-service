package user_service.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import user_service.request.CreateUserRequest;
import user_service.response.CreateUserRes;
import user_service.response.GetUsersRes;
import user_service.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public CreateUserRes createUserRes(@RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @GetMapping("/allUsers")
    public GetUsersRes getAllUsers() {
        return userService.getUsers();
    }
}
