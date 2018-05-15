package common.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Board implements Serializable {

    @Id
    private String id;
    private String name;
    private String ownerId;
    private String description;
    private List<String> usersIds;
}
