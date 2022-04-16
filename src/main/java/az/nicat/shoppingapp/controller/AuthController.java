package az.nicat.shoppingapp.controller;

import az.nicat.shoppingapp.model.dto.JwtDto;
import az.nicat.shoppingapp.model.dto.UserDto;
import az.nicat.shoppingapp.model.request.LoginRequest;
import az.nicat.shoppingapp.model.request.RegisterRequest;
import az.nicat.shoppingapp.model.response.BaseResponse;
import az.nicat.shoppingapp.security.AuthService;
import az.nicat.shoppingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<JwtDto>> login(
            @RequestBody @Valid LoginRequest loginRequest){
        JwtDto jwt = authService.login(loginRequest);
        return ResponseEntity.ok(new BaseResponse<>(Boolean.TRUE, "You were successfully logged in", jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserDto>> register(
            @RequestBody @Valid RegisterRequest request){
        UserDto user = userService.register(request);
        return ResponseEntity.ok(new BaseResponse<>(Boolean.TRUE, "You were successfully registered", user));
    }
}
