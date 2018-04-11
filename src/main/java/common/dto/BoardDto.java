package common.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BoardDto {

    private String id;
    @NotNull
    @NotEmpty
    private String name;
    private String description;
    private List<String> users;
    @NotEmpty
    @NotNull
    private String ownerLogin;

    @Builder
    public BoardDto(String id, String name, String description, List<String> users, String ownerLogin) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.users = users;
        this.ownerLogin = ownerLogin;
    }
}