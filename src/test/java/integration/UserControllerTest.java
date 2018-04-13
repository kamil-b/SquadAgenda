package integration;

import com.google.gson.Gson;
import common.SquadAgendaApplication;
import common.dto.CreateUserDto;
import common.model.User;
import common.model.enums.Role;
import common.repository.UserRepository;
import common.web.UserController;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;

import javax.servlet.ServletContext;
import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SquadAgendaApplication.class)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private Gson gson = new Gson();

    @MockBean
    private UserRepository userRepository;

    @Before
    public void init(){
         mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("userController"));
    }

    @Test
    public void givenUserId_whenGet_thenReturnUserDto() throws Exception {
        String userId = "32oymd95j4bdu5403htmv";

        val user = User.builder()
                .id(userId)
                .email("test@email.com")
                .username("tester")
                .role(Role.USER)
                .boards(new ArrayList<>())
                .events(new ArrayList<>())
                .ownedBoards(new ArrayList<>())
                .build();


        when(userRepository.findById(userId)).thenReturn(Mono.just(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.role").value(user.getRole().toString()));
    }

    @Test
    public void givenUser_whenCreate_returnNewUser() throws Exception {

        val createUserDto = CreateUserDto.builder()
                .username("tester")
                .email("tester.manualny@gmail.com")
                .password("password")
                .role("USER")
                .build();

        val expectedId = "1234432131";
        val expectedUser = User.builder().id(expectedId)
                .username(createUserDto.getUsername())
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .role(Role.valueOf(createUserDto.getRole()))
                .boards(new ArrayList<>())
                .ownedBoards(new ArrayList<>())
                .events(new ArrayList<>())
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(expectedUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(createUserDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(expectedId))
                .andExpect(jsonPath("$.username").value(createUserDto.getUsername()))
                .andExpect(jsonPath("$.email").value(createUserDto.getEmail()))
                .andExpect(jsonPath("$.role").value(createUserDto.getRole()));
    }
}
