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

    @Autowired
    private BoardService boardService;

    @GetMapping(value = "{id}")
    public ResponseEntity<BoardDto> getBoardById(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(boardService.findById(id));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<BoardDto> saveBoard(@RequestBody @Valid BoardDto boardDto) {
        return ResponseEntity.ok(boardService.createBoard(boardDto));
    }

}
