package project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.exception.BadRequestException;
import project.service.UserService;

@Tag(name = "Users", description = "User management endpoints")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUser(@RequestParam String username) {
        if (username.isBlank()) {
            throw new BadRequestException("Username can not be empty");
        }
        return userService.createUser(username);
    }
}
