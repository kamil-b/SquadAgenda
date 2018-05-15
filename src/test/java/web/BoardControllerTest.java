package web;

import common.model.Board;
import common.repository.BoardRepository;
import common.repository.UserRepository;
import common.service.BoardService;
import common.web.BoardController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.mock;

public class BoardControllerTest {

    private WebTestClient webTestClient;
    private BoardRepository boardRepository;
    private UserRepository userRepository;


    @Before
    public void setup() {
        boardRepository = mock(BoardRepository.class);
        userRepository = mock(UserRepository.class);

        webTestClient = WebTestClient.bindToController(new BoardController(
                new BoardService(userRepository, boardRepository)))
                .configureClient()
                .baseUrl("/board")
                .build();
    }

/*    @Test
    public void getBoard(){
        Board board = Board.builder()
                .id("13123")
                .name("board")
                .owner("")
    }*/

}
