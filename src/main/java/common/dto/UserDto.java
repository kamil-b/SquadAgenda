package common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private String id;
    private String username;
    private String email;
    private String role;
    private List<String> boards;
    private List<String> ownedBoards;
    private List<EventDto> events;

    @Builder
    public UserDto(String id, String username, String email, String role, List<String> boards, List<String> ownedBoards, List<EventDto> events) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.boards = boards;
        this.ownedBoards = ownedBoards;
        this.events = events;
    }
}