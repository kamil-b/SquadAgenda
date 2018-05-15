package common.web;

import common.dto.CreateUserDto;
import common.dto.UserDto;
import common.repository.BoardRepository;
import common.repository.UserRepository;
import common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/userId")
public class UserController {

    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable(value = "id") String id) {
        return userRepository.findById(id)
                .flatMap(UserService::buildUserDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/registration")
    public Mono<ResponseEntity<UserDto>> registerUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return Mono.just(createUserDto)
                .flatMap(UserService::createUserEntity)
                .flatMap(userRepository::save)
                .flatMap(UserService::buildUserDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED));
    }

    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<UserDto>> deleteUser(@PathVariable(value = "id") String id) {
        return userRepository.findById(id)
                .flatMap(user -> userRepository.delete(user).then(Mono.just(user)))
                .flatMap(UserService::buildUserDto)
                .map(user -> ResponseEntity.status(HttpStatus.ACCEPTED).body(user))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "")
    public Mono<ResponseEntity<UserDto>> updateUser(@Valid @RequestBody UserDto userDto) {
        return userService.update(userDto)
                .map(user -> ResponseEntity.status(HttpStatus.ACCEPTED).body(user));
    }
}