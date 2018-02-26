package common.web;

import common.dto.CreateUserDto;
import common.dto.UserDto;
import common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@Valid @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(userService.registerNewUser(createUserDto));
    }
}
