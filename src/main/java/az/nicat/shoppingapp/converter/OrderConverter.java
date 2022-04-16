package az.nicat.shoppingapp.converter;

import az.nicat.shoppingapp.model.dto.OrderDto;
import az.nicat.shoppingapp.model.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter extends BaseConverter<Order, OrderDto> {

    @Override
    public OrderDto convert(Order from) {
        return new OrderDto(
                from.getId(),
                from.getCount(),
                from.getUser().getId(),
                from.getProduct().getId(),
                from.getCreatedAt(),
                from.getUpdatedAt()
        );
    }

    @Override
    public List<OrderDto> convert(List<Order> from) {
        if (from == null) return List.of();
        return from.stream().map(this::convert).collect(Collectors.toList());
    }
}
