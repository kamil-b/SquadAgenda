package common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"owner", "users"})
public class Board implements Serializable {

    @Id
    private String id;
    private String name;
    private User owner;
    private String description;
    private List<User> users = new ArrayList<>();
}
