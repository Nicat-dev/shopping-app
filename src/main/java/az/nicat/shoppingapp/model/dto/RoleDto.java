package az.nicat.shoppingapp.model.dto;

import az.nicat.shoppingapp.model.enums.ERole;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class RoleDto  {
    private final ERole roleName;
}
