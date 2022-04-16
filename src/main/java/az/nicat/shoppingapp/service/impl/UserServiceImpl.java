package az.nicat.shoppingapp.service.impl;

import az.nicat.shoppingapp.converter.UserConverter;
import az.nicat.shoppingapp.exception.ResourceExistsException;
import az.nicat.shoppingapp.exception.ResourceNotFoundException;
import az.nicat.shoppingapp.model.dto.UserDto;
import az.nicat.shoppingapp.model.entity.Role;
import az.nicat.shoppingapp.model.entity.User;
import az.nicat.shoppingapp.model.enums.ERole;
import az.nicat.shoppingapp.model.request.RegisterRequest;
import az.nicat.shoppingapp.model.request.UpdateUserRequest;
import az.nicat.shoppingapp.repository.UserRepository;
import az.nicat.shoppingapp.service.RoleService;
import az.nicat.shoppingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static az.nicat.shoppingapp.model.enums.ERole.ROLE_USER;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserConverter converter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    @Override
    @Transactional
    public UserDto register(RegisterRequest request) {
        validateNewUsernameAndEmail(request.getUsername(),request.getEmail());
        Role role = roleService.getBy(ROLE_USER);
        User user = repository.saveAndFlush(mapTo(request,List.of(role)));
        return converter.convert(user);
    }


    @Override
    public UserDto getById(Long id) {
        log.info("Get user with id:{}",id);
        return converter.convert(getBy(id));
    }

    @Override
    public UserDto getByUsername(String username) {
        log.info("Get user with username:{}",username);
        return converter.convert(getBy(username));
    }

    @Override
    @Transactional
    public UserDto updateUserRequest(Long id, UpdateUserRequest updateUserRequest) {
        User user = getBy(id);
        if (!user.getUsername().equals(updateUserRequest.getUserName())){
            existsByUsername(updateUserRequest.getUserName());
            user.setUsername(updateUserRequest.getUserName());
        }
        user.setName(updateUserRequest.getFullName());
        return converter.convert(repository.saveAndFlush(user));
    }

    @Override
    public List<UserDto> getAll() {
        log.info("All of user fetching ...");
        return converter.convert(repository.findAll());
    }

    @Override
    public List<UserDto> getAllActive() {
        return converter.convert(getAllByIsActive(TRUE));
    }

    @Override
    public List<UserDto> getAllDeactivated() {
        return converter.convert(getAllByIsActive(FALSE));
    }

    @Override
    public User getBy(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",id));
    }

    @Override
    public User getBy(String userName) {
        return repository.findByUsername(userName)
                .orElseThrow(()-> new ResourceNotFoundException("User","username",userName));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Delete user with id:{}",id);
        repository.delete(getBy(id));
    }

    @Override
    @Transactional
    public void active(Long id) {
        log.info("Active user with id:{}",id);
        changeIsActive(id,TRUE);
    }

    @Override
    public void deActivate(Long id) {
        log.info("deActive user with id:{}",id);
        changeIsActive(id,FALSE);
    }

    @Override
    public void locked(Long id) {
        log.info("locked user with id:{}",id);
        changeIsLocked(id,FALSE);
    }

    @Override
    public void unLocked(Long id) {
        log.info("unLocked user with id:{}",id);
        changeIsLocked(id,FALSE);
    }

    private List<User> getAllByIsActive(Boolean isActive){
        return repository.findUsersByIsActive(isActive);
    }

    private void changeIsActive(Long id ,Boolean isActive){
        User user = getBy(id);
        user.setIsActive(isActive);
        repository.save(user);
    }

    private void changeIsLocked(Long id ,Boolean isNotLocked){
        User user = getBy(id);
        user.setIsNotLocked(isNotLocked);
        repository.save(user);
    }

    private boolean existsByUsername(String username){
        return repository.existsByUsername(username);
    }

    private void validateNewUsernameAndEmail(String username, String email) {
        if (existsByUsername(username))
            throw new ResourceExistsException("User","username",username);
        if (existsByEmail(email))
            throw new ResourceExistsException("User","email",email);
    }

    private boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    private User mapTo(RegisterRequest request,List<Role> roles){
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .isActive(TRUE)
                .roles(roles)
                .isNotLocked(TRUE)
                .name(request.getName())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build();
    }

}
