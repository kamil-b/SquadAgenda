package common.service;

import common.dto.UserDto;
import common.entities.Board;
import common.entities.User;
import common.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

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
                .build();
    }

    private List<String> getBoardsNames(List<Board> boards) {
        return boards.stream().map(Board::getName).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
