package common.service;

import common.dto.CreateUserDto;
import common.dto.EventDto;
import common.dto.UserDto;
import common.model.Board;
import common.model.Event;
import common.model.User;
import common.model.enums.Role;
import common.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")

@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventService eventService;

    public Mono<UserDto> findById(String id) {
        return userRepository.findById(id).flatMap(UserService::buildUserDto);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public Mono<UserDto> registerNewUser(CreateUserDto createUserDto) {
        return Mono.just(createUserDto)
                .flatMap(UserService::createUserEntity)
                .flatMap(userRepository::save)
                .flatMap(UserService::buildUserDto);
    }

    public static List<EventDto> buildEventsDtos(List<Event> events) {
        return events.stream().map(EventService::buildEventDto).collect(Collectors.toList());
    }

    private static List<String> getBoardsNames(List<Board> boards) {
        return boards.stream().map(Board::getName).collect(Collectors.toList());
    }


    private static Mono<User> createUserEntity(CreateUserDto createUserDto) {
        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setPassword(createUserDto.getPassword());
        user.setEmail(createUserDto.getEmail());
        user.setRole(Role.valueOf(createUserDto.getRole()));
        return Mono.just(user);
    }

    private static Mono<UserDto> buildUserDto(User user) {
        return Mono.just(UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole().toString())
                .boards(getBoardsNames(user.getBoards()))
                .ownedBoards(getBoardsNames(user.getOwnedBoards()))
                .events(buildEventsDtos(user.getEvents()))
                .build());
    }
}