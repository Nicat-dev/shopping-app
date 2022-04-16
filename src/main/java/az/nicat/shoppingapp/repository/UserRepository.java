package az.nicat.shoppingapp.repository;

import az.nicat.shoppingapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
    List<User> findUsersByIsActive(Boolean isActive);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}