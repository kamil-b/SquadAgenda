package common.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "board")
@EqualsAndHashCode(exclude = {"owner", "users"})
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User owner;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonBackReference
    private List<User> users = new ArrayList<>();
}
