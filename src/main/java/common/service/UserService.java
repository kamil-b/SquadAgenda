package common.service;

import common.dto.CreateUserDto;
import common.dto.EventDto;
import common.dto.UserDto;
import common.model.Board;
import common.model.Event;
import common.model.User;
import common.model.enums.Role;
import common.repository.BoardRepository;
import common.repository.EventRepository;
import common.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service("userDetailsService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final EventRepository eventRepository;

    public UserService(UserRepository userRepository, BoardRepository boardRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    private static List<EventDto> buildEventsDtos(List<Event> events) {
        return events.stream().map(EventService::buildEventDto).collect(Collectors.toList());
    }

    private static List<String> getBoardsNames(List<Board> boards) {
        return boards.stream().map(Board::getName).collect(Collectors.toList());
    }

    public static Mono<User> createUserEntity(CreateUserDto createUserDto) {
        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setPassword(createUserDto.getPassword());
        user.setEmail(createUserDto.getEmail());
        user.setRole(Role.valueOf(createUserDto.getRole()));
        return Mono.just(user);
    }

    public static Mono<UserDto> buildUserDto(User user) {
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

    public Mono<UserDto> update(UserDto userDto) {
        List<Board> boards = userDto.getBoards()
                .stream()
                .map(name -> boardRepository.findByName(name).block())
                .collect(Collectors.toList());

        List<Board> ownedBoards = userDto.getBoards()
                .stream()
                .map(name -> boardRepository.findByName(name).block())
                .collect(Collectors.toList());

        List<Event> events = userDto.getEvents().stream()
                .map(EventDto::getId)
                .map(id -> eventRepository.findById(id).block())
                .collect(Collectors.toList());

        return userRepository.findById(userDto.getId())
                .flatMap(user -> updateUserEntity(user, userDto, boards, ownedBoards, events))
                .flatMap(updated -> userRepository.save(updated))
                .flatMap(UserService::buildUserDto);
    }

    private static Mono<User> updateUserEntity(User user, UserDto dto, List<Board> boards, List<Board> ownedBoards, List<Event> events) {
        user.setEmail(dto.getEmail());
        user.setRole(Role.valueOf(dto.getRole()));
        user.setUsername(dto.getUsername());
        user.setEvents(events);
        user.setBoards(boards);
        user.setOwnedBoards(ownedBoards);
        return Mono.just(user);
    }
}