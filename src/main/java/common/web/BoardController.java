package common.web;

import common.dto.BoardDto;
import common.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<BoardDto> getBoardById(@Valid @PathVariable("id") String id) {
        return ResponseEntity.ok(boardService.findById(id));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<BoardDto> saveBoard(@RequestBody @Valid BoardDto boardDto) {
        return ResponseEntity.ok(boardService.createBoard(boardDto));
    }

}
