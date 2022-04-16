package az.nicat.shoppingapp.model.request;

import az.nicat.shoppingapp.annotation.MatchPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank @NotEmpty @NotNull
    private String username;
    @MatchPassword
    private String password;
}
