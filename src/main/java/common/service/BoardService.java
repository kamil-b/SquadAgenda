package common.service;

import common.dto.BoardDto;
import common.entities.Board;
import common.entities.User;
import common.repository.BoardRepository;
import common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BoardService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    public BoardDto createBoard(BoardDto boardDto) {
        User user = userRepository.findByLogin(boardDto.getOwnerLogin()).orElseThrow(IllegalArgumentException::new);

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
               .ownerLogin(board.getOwner().getLogin())
                .description(board.getDescription())
                .users(board.getUsers())
                .build();
    }


}
