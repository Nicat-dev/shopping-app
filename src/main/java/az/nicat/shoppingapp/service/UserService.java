package az.nicat.shoppingapp.service;

import az.nicat.shoppingapp.model.dto.UserDto;
import az.nicat.shoppingapp.model.entity.User;
import az.nicat.shoppingapp.model.request.RegisterRequest;
import az.nicat.shoppingapp.model.request.UpdateUserRequest;

import java.util.List;

public interface UserService {
    UserDto register(RegisterRequest request);
    UserDto getById(Long id);
    UserDto getByUsername(String username);
    UserDto updateUserRequest(Long id ,UpdateUserRequest updateUserRequest);
    List<UserDto> getAll();
    List<UserDto> getAllActive();
    List<UserDto> getAllDeactivated();
    User getBy(Long id);
    User getBy(String userName);
    void delete(Long id);
    void active(Long id);
    void deActivate(Long id);
    void locked(Long id);
    void unLocked(Long id);
}
