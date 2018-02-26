package common.service;

import common.dto.CreateUserDto;
import common.dto.EventDto;
import common.dto.UserDto;
import common.exception.EmailAddressAlreadyExistsException;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userDetailsService")
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventService eventService;

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return buildUserDto(user);
    }

    private UserDto buildUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole().toString())
                .boards(getBoardsNames(user.getBoards()))
                .ownedBoards(getBoardsNames(user.getOwnedBoards()))
                .events(buildEventsDtos(user.getEvents()))
                .build();
    }

    private List<EventDto> buildEventsDtos(List<Event> events) {
        return events.stream().map(event -> eventService.buildEventDto(event)).collect(Collectors.toList());
    }

    private List<String> getBoardsNames(List<Board> boards) {
        return boards.stream().map(Board::getName).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDto registerNewUser(CreateUserDto createUserDto) {
        if (emailExists(createUserDto.getEmail())) {
            throw new EmailAddressAlreadyExistsException();
        }
        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setPassword(createUserDto.getPassword());
        user.setEmail(createUserDto.getEmail());
        user.setRole(Role.valueOf(createUserDto.getRole()));
        User savedUser = userRepository.save(user);
        return buildUserDto(savedUser);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
