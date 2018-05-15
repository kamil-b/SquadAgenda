package common.service;

import common.dto.BoardDto;
import common.model.Board;
import common.model.User;
import common.repository.BoardRepository;
import common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    public BoardService(UserRepository userRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }


    public Mono<BoardDto> createBoard(BoardDto boardDto) {
        return userRepository.findByUsername(boardDto.getOwnerLogin())
                .map(user -> Board.builder()
                        .name(boardDto.getName())
                        .description(boardDto.getDescription())
                        .ownerId(((User) user).getId())
                        .usersIds(boardDto.getUsers())
                        .build())
                .flatMap(boardRepository::save)
                .flatMap(BoardService::buildBoardDto);

    }

    public Mono<BoardDto> findById(String id) {
        Board board = boardRepository.findById(id).block();
        return buildBoardDto(board);
    }

    private static Mono<BoardDto> buildBoardDto(Board board) {
        return Mono.just(BoardDto.builder()
                .id(board.getId())
                .name(board.getName())
                .ownerLogin(board.getOwnerId())
                .description(board.getDescription())
                .users(board.getUsersIds())
                .build());
    }

    private List<String> getUsersNames(List<User> users) {
        return users.stream().map(User::getUsername).collect(Collectors.toList());
    }
}