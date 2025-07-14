package user_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import user_service.request.CreateUserReq;
import user_service.request.UpdateUserReq;
import user_service.request.RegisterReq;
import user_service.response.CreateUserRes;
import user_service.response.GetUsersRes;
import user_service.response.UpdateUserRes;
import user_service.response.RegisterRes;
import user_service.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public CreateUserRes createUserRes(@RequestBody CreateUserReq createUserReq) {
        return userService.createUser(createUserReq);
    }

    @GetMapping("/allUsers")
    public GetUsersRes getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/id/{id}")
    public GetUsersRes getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/name/{name}")
    public GetUsersRes getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("/surname/{surname}")
    public GetUsersRes getUserBySurname(@PathVariable String surname) {
        return userService.getUserBySurname(surname);
    }

    @GetMapping("/{name}/{surname}")
    public GetUsersRes getUserByNameAndSurname(@PathVariable String name,@PathVariable String surname) {
        return userService.getUserByNameAndSurname(name, surname);
    }

    @PutMapping("/update/{id}")
    public UpdateUserRes updateUser(@PathVariable Long id, @RequestBody UpdateUserReq updateUserReq) {
        return userService.updateUser(id, updateUserReq);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
