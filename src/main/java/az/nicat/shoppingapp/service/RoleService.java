package az.nicat.shoppingapp.service;

import az.nicat.shoppingapp.model.dto.RoleDto;
import az.nicat.shoppingapp.model.entity.Role;
import az.nicat.shoppingapp.model.enums.ERole;

import java.util.List;

public interface RoleService {
    RoleDto create(RoleDto roleDto);
    RoleDto getByRoleName(ERole roleName);
    List<RoleDto> getAll();
    Role getBy(ERole roleName);
}
