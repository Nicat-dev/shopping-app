package az.nicat.shoppingapp.security;

import az.nicat.shoppingapp.model.entity.User;
import az.nicat.shoppingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username: %s",username)));
        foundUser.setLastLoginAt(LocalDateTime.now());
        User user = repository.saveAndFlush(foundUser);
        return new UserDetailsImpl(user);
    }
}
