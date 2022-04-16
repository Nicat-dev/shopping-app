package az.nicat.shoppingapp.service.impl;

import az.nicat.shoppingapp.converter.RoleConverter;
import az.nicat.shoppingapp.exception.ResourceNotFoundException;
import az.nicat.shoppingapp.model.dto.RoleDto;
import az.nicat.shoppingapp.model.entity.Role;
import az.nicat.shoppingapp.model.enums.ERole;
import az.nicat.shoppingapp.repository.RoleRepository;
import az.nicat.shoppingapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final RoleConverter converter;

    @Override
    public RoleDto create(RoleDto roleDto) {
        Role role = repository.saveAndFlush(new Role(roleDto.getRoleName()));
        return converter.convert(role);
    }

    @Override
    public RoleDto getByRoleName(ERole roleName) {
        return converter.convert(getBy(roleName));
    }

    @Override
    public List<RoleDto> getAll() {
        return null;
    }

    @Override
    public Role getBy(ERole roleName) {
        return repository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "roleName", roleName));
    }
}
