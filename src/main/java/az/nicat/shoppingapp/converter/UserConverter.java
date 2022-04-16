package az.nicat.shoppingapp.converter;

import az.nicat.shoppingapp.model.dto.UserDto;
import az.nicat.shoppingapp.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverter extends BaseConverter<User, UserDto> {

    private final OrderConverter orderConverter;
    private final RoleConverter roleConverter;

    @Override
    public UserDto convert(User from) {
        return new UserDto(
                from.getId(),
                from.getName(),
                from.getUsername(),
                from.getEmail(),
                from.getPassword(),
                roleConverter.convert(from.getRoles()),
                orderConverter.convert(from.getOrders()),
                from.getIsActive(),
                from.getIsNotLocked(),
                from.getLastLoginAt(),
                from.getCreatedAt(),
                from.getUpdatedAt()

        );
    }


    @Override
    public List<UserDto> convert(List<User> from) {
        if (from == null) return List.of();
        return from.stream().map(this::convert).collect(Collectors.toList());
    }
}
