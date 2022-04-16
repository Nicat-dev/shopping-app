package az.nicat.shoppingapp.repository;

import az.nicat.shoppingapp.model.entity.Role;
import az.nicat.shoppingapp.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, ERole> {
    Optional<Role> findByRoleName(ERole roleName);
}