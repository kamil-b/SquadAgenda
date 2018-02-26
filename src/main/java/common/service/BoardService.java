package common.service;

import common.dto.BoardDto;
import common.model.Board;
import common.model.User;
import common.repository.BoardRepository;
import common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BoardService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    public BoardDto createBoard(BoardDto boardDto) {
        User user = userRepository.findByUsername(boardDto.getOwnerLogin()).orElseThrow(IllegalArgumentException::new);

        Board board = new Board();
        board.setName(boardDto.getName());
        board.setDescription(boardDto.getDescription());
        board.setOwner(user);

        return buildBoardDto(boardRepository.save(board));
    }

    public BoardDto findById(long id) {
        Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
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
