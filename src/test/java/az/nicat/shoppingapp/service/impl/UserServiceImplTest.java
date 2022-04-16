package az.nicat.shoppingapp.service.impl;


import az.nicat.shoppingapp.converter.UserConverter;
import az.nicat.shoppingapp.model.request.RegisterRequest;
import az.nicat.shoppingapp.repository.UserRepository;
import az.nicat.shoppingapp.service.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceImplTest {

    private UserServiceImpl userService;

    private  UserRepository repository;
    private  BCryptPasswordEncoder bCryptPasswordEncoder;
    private  UserConverter converter;
    private  RoleService roleService;

    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(UserRepository.class);
        bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        converter = Mockito.mock(UserConverter.class);
        roleService = Mockito.mock(RoleService.class);

        userService = new UserServiceImpl(repository,converter,bCryptPasswordEncoder,roleService);

    }

    @Test
    public void whenRegisterCalledWithValidRequest_itShouldReturnValidUserDto(){
        RegisterRequest request = new  RegisterRequest();
        request.setName("nicat");
        request.setEmail("mehtiyev.n@gmail.com");
        request.setUsername("nicatvev11");
        request.setPassword("nicat334422");
    }
}