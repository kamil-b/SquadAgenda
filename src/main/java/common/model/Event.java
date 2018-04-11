package common.model;

import common.model.enums.EventType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(exclude = "user")
@Data
public class Event {

    @Id private String id;
    private EventType type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private User user;

}
