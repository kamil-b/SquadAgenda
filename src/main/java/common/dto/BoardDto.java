package common.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class BoardDto {

    private String id;
    @NotEmpty
    private String name;
    private String description;
    private List<String> users;
    @NotEmpty
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