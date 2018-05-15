package web;

import common.dto.CreateUserDto;
import common.dto.UserDto;
import common.model.User;
import common.model.enums.Role;
import common.repository.BoardRepository;
import common.repository.EventRepository;
import common.repository.UserRepository;
import common.service.UserService;
import common.web.UserController;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class UserControllerTest {

    private WebTestClient webTestClient;
    private BoardRepository boardRepository;
    private EventRepository eventRepository;
    private UserRepository userRepository;


    @Before
    public void setup() {
        userRepository = mock(UserRepository.class);
        boardRepository = mock(BoardRepository.class);
        eventRepository = mock(EventRepository.class);
        UserService userService = new UserService(userRepository, boardRepository, eventRepository);

        webTestClient = WebTestClient.bindToController(new UserController(userRepository, userService))
                .configureClient()
                .baseUrl("/userId")
                .build();
    }

    @Test
    public void testCreateUser() {

        val createUserDto = CreateUserDto.builder()
                .username("tester")
                .email("tester.manualny@gmail.com")
                .password("password")
                .role("USER")
                .build();

        val user = User.builder()
                .id("23424sdfa34d3ikn4")
                .email(createUserDto.getEmail())
                .username(createUserDto.getUsername())
                .role(Role.valueOf(createUserDto.getRole()))
                .boards(new ArrayList<>())
                .events(new ArrayList<>())
                .ownedBoards(new ArrayList<>())
                .build();

        when(userRepository.save(any())).thenReturn(Mono.just(user));

        this.webTestClient.post().uri("/registration")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(fromObject(createUserDto))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.username").isEqualTo("tester")
                .jsonPath("$.email").isEqualTo("tester.manualny@gmail.com")
                .jsonPath("$.role").isEqualTo("USER");
    }

    @Test
    public void testGetUserById() {

        val user = User.builder()
                .id("23424sdfa34d3ikn4")
                .email("tester.manualny@gmail.com")
                .username("tester")
                .role(Role.USER)
                .boards(new ArrayList<>())
                .events(new ArrayList<>())
                .ownedBoards(new ArrayList<>())
                .build();

        when(userRepository.findById(anyString())).thenReturn(Mono.just(user));

        this.webTestClient.get().uri("/userId" + user.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isEqualTo(user.getId())
                .jsonPath("$.username").isEqualTo("tester")
                .jsonPath("$.email").isEqualTo("tester.manualny@gmail.com")
                .jsonPath("$.role").isEqualTo("USER");
    }

    @Test
    public void testDeleteUserById() {
        val user = User.builder()
                .id("23424sdfa34d3ikn4")
                .email("tester.manualny@gmail.com")
                .username("tester")
                .role(Role.USER)
                .boards(new ArrayList<>())
                .events(new ArrayList<>())
                .ownedBoards(new ArrayList<>())
                .build();

        when(userRepository.findById(anyString())).thenReturn(Mono.just(user));
        when(userRepository.delete(any())).thenReturn(Mono.empty());

        this.webTestClient.delete().uri("/userId" + user.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isEqualTo(user.getId())
                .jsonPath("$.username").isEqualTo("tester")
                .jsonPath("$.email").isEqualTo("tester.manualny@gmail.com")
                .jsonPath("$.role").isEqualTo("USER");
    }

    @Test
    public void testWhenUpdateUserReturnUpdated() {
        val userDto = UserDto.builder()
                .id("23424sdfa34d3ikn4")
                .username("tester")
                .email("new@mail.com")
                .boards(new ArrayList<>())
                .ownedBoards(new ArrayList<>())
                .events(new ArrayList<>())
                .role("USER")
                .build();

        val user = User.builder()
                .id(userDto.getId())
                .email("old@mail.com")
                .username("super_tester")
                .role(Role.ADMIN)
                .boards(new ArrayList<>())
                .events(new ArrayList<>())
                .ownedBoards(new ArrayList<>())
                .build();

        when(userRepository.findById(userDto.getId())).thenReturn(Mono.just(user));
        when(userRepository.delete(user)).thenReturn(Mono.empty());
        when(userRepository.save(user)).thenReturn(Mono.just(user));
        when(boardRepository.findByName(anyString())).thenReturn(Mono.empty());
        when(eventRepository.findById(anyString())).thenReturn(Mono.empty());

        this.webTestClient.put().uri("")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(fromObject(userDto))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isEqualTo("23424sdfa34d3ikn4")
                .jsonPath("$.username").isEqualTo("tester")
                .jsonPath("$.email").isEqualTo("new@mail.com")
                .jsonPath("$.role").isEqualTo("USER");
    }
}