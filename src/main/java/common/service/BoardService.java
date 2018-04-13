package common.service;

import common.dto.BoardDto;
import common.model.Board;
import common.model.User;
import common.repository.BoardRepository;
import common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    public BoardDto createBoard(BoardDto boardDto) {
        Mono<UserDetails> user = userRepository.findByUsername(boardDto.getOwnerLogin());

        Board board = new Board();
        board.setName(boardDto.getName());
        board.setDescription(boardDto.getDescription());
        board.setOwner((User) user.block());

        return buildBoardDto(boardRepository.save(board).block());
    }

    public BoardDto findById(String id) {
        Board board = boardRepository.findById(id).block();
        return buildBoardDto(board);
    }

    private BoardDto buildBoardDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .name(board.getName())
                .ownerLogin(board.getOwner().getUsername())
                .description(board.getDescription())
                .users(getUsersNames(board.getUsers()))
                .build();
    }

    private List<String> getUsersNames(List<User> users) {
        return users.stream().map(User::getUsername).collect(Collectors.toList());
    }
}