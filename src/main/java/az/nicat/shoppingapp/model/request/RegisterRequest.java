package az.nicat.shoppingapp.model.request;

import az.nicat.shoppingapp.annotation.MatchPassword;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotNull @NotBlank @NotEmpty
    private String name;
    @NotNull @NotBlank @NotEmpty
    private String username;
    @Email
    private String email;
    @MatchPassword
    private String password;
}
