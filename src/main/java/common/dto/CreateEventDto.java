package common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventDto {

    @NotEmpty
    private String type;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotEmpty
    private String username;
}