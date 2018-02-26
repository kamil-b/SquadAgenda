package common.dto;

import common.annotation.ValidEmail;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@Data
public class CreateUserDto {

    @NotEmpty
    @Size(min = 3, max = 100)
    private String username;
    @NotEmpty
    @ValidEmail
    private String email;
    @NotEmpty
    private String role;
    @NotEmpty
    @Size(min = 5, max = 100)
    private String password;
}