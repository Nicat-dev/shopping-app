package az.nicat.shoppingapp.service;

import az.nicat.shoppingapp.model.dto.OrderDto;
import az.nicat.shoppingapp.model.entity.Order;
import az.nicat.shoppingapp.model.request.CreateOrderRequest;
import az.nicat.shoppingapp.model.request.UpdateOrderRequest;

import java.util.List;

public interface OrderService {
    OrderDto getById(Long id);
    List<OrderDto> getAll();
    OrderDto create(CreateOrderRequest createOrderRequest);
    OrderDto update(Long id,UpdateOrderRequest updateOrderRequest);
    Order getBy(Long id);
    void delete(Long id);
}
