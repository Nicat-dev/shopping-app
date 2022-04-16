package az.nicat.shoppingapp.converter;

import az.nicat.shoppingapp.model.dto.RoleDto;
import az.nicat.shoppingapp.model.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleConverter extends BaseConverter<Role,RoleDto> {
    @Override
    public RoleDto convert(Role from) {
        return new RoleDto(from.getRoleName());
    }

    @Override
    public List<RoleDto> convert(List<Role> from) {
        if (from == null) return List.of();
        return from.stream().map(this::convert).collect(Collectors.toList());
    }
}
