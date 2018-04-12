package integration;

import com.google.gson.Gson;
import common.SquadAgendaApplication;
import common.dto.CreateUserDto;
import common.model.User;
import common.model.enums.Role;
import common.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SquadAgendaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private Gson gson = new Gson();

    @MockBean
    UserRepository userRepository;

    @Test
    public void givenUser_whenCreate_returnNewUser() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

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

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(createUserDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(expectedId))
                .andExpect(jsonPath("$.username").value(createUserDto.getUsername()))
                .andExpect(jsonPath("$.email").value(createUserDto.getEmail()))
                .andExpect(jsonPath("$.role").value(createUserDto.getRole()));
    }
}
