package common.dto;

import common.entities.User;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class BoardDto {

    private Long id;
    @NotNull
    @NotEmpty
    private String name;
    private String description;
    private Set<User> users;
    @NotEmpty
    @NotNull
    private String ownerLogin;

    @Builder
    public BoardDto(Long id, String name, String description, Set<User> users, String ownerLogin) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.users = users;
        this.ownerLogin = ownerLogin;
    }

    public static class BoardDtoBuilder{

    }
}
