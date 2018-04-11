import com.google.gson.Gson;
import common.dto.CreateUserDto;
import common.web.UserController;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private Gson gson = new Gson();

    @Test
    public void givenUser_whenCreate_returnNewUser() throws Exception {
        val createUserDto = CreateUserDto.builder()
                .username("tester")
                .email("tester@gft.com")
                .password("password")
                .role("USER")
                .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(createUserDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.username").value(createUserDto.getUsername()))
                .andExpect(jsonPath("$.email").value(createUserDto.getEmail()))
                .andExpect(jsonPath("$.password").value(createUserDto.getPassword()))
                .andExpect(jsonPath("$.role").value(createUserDto.getRole()));
    }
}
