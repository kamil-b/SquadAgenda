package common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class EventDto extends CreateEventDto {

    private String id;

    @Builder
    public EventDto(String id, String type, Date date, String username) {
        super(type, date, username);
        this.id = id;
    }
}