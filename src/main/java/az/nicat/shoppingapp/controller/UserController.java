package az.nicat.shoppingapp.controller;

import az.nicat.shoppingapp.model.dto.UserDto;
import az.nicat.shoppingapp.model.request.UpdateUserRequest;
import az.nicat.shoppingapp.model.response.BaseResponse;
import az.nicat.shoppingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/name/{username}")
    public ResponseEntity<BaseResponse<UserDto>> getByUsername(@PathVariable String userName){
        UserDto user = userService.getByUsername(userName);
        return ResponseEntity.ok(new BaseResponse<>(Boolean.TRUE,"profile successfully found",user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserDto>> getById(@PathVariable Long id){
        UserDto userDto = userService.getById(id);
        return ResponseEntity.ok(new BaseResponse<>(Boolean.TRUE,"profile successfully finder",userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<UserDto>> update(@RequestBody @Valid UpdateUserRequest request,@PathVariable Long id){
        UserDto user = userService.updateUserRequest(id,request);
        return ResponseEntity.ok(new BaseResponse<>(Boolean.TRUE,"your were successfully updated Profile",user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<UserDto>> delete(@PathVariable Long id){
        UserDto userDto = userService.getById(id);
        userService.delete(id);
        return ResponseEntity.ok(new BaseResponse<>(Boolean.TRUE,"your profile succesfully deleted",userDto));
    }
}
