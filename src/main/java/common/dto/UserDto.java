package common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private List<String> boards;
    private List<String> ownedBoards;

    @Builder
    public UserDto(Long id, String username, String password, String email, String role, List<String> boards, List<String> ownedBoards) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.boards = boards;
        this.ownedBoards = ownedBoards;
    }

    public static class UserDtoBuilder {
    }
}
