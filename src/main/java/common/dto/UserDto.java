package common.dto;

import lombok.Builder;
import lombok.Data;

import javax.management.relation.Role;

@Data
public class UserDto {

    private Long id;
    private String login;
    private String password;
    private String email;
    private String role;

    @Builder
    public UserDto(Long id, String login, String password, String email, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public static class UserDtoBuilder {

    }
}
