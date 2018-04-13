package common.web;

import common.dto.CreateUserDto;
import common.dto.UserDto;
import common.repository.UserRepository;
import common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public Mono<UserDto> getUserById(@Valid @PathVariable(value = "id") String id) {
        return userService.findById(id);
    }

    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> registerUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return userService.registerNewUser(createUserDto)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(dto));
    }
}