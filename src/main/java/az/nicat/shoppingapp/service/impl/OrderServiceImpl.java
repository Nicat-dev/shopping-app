package az.nicat.shoppingapp.service.impl;

import az.nicat.shoppingapp.converter.OrderConverter;
import az.nicat.shoppingapp.exception.ResourceNotFoundException;
import az.nicat.shoppingapp.model.dto.OrderDto;
import az.nicat.shoppingapp.model.entity.Order;
import az.nicat.shoppingapp.model.request.CreateOrderRequest;
import az.nicat.shoppingapp.model.request.UpdateOrderRequest;
import az.nicat.shoppingapp.repository.OrderRepository;
import az.nicat.shoppingapp.service.OrderService;
import az.nicat.shoppingapp.service.ProductService;
import az.nicat.shoppingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public OrderDto getById(Long id) {
        return orderConverter.convert(orderRepository.getById(id));
    }

    @Override
    public List<OrderDto> getAll() {
        return null;
    }

    @Override
    @Transactional
    public OrderDto create(CreateOrderRequest createOrderRequest) {
        Order order = Order.builder()
                .user(userService.getBy(createOrderRequest.getUserId()))
                .product(productService.getBy(createOrderRequest.getProductId()))
                .count(createOrderRequest.getCount())
                .build();
        Order savedOrder = orderRepository.saveAndFlush(order);
        return orderConverter.convert(savedOrder);
    }

    @Override
    public OrderDto update(Long id, UpdateOrderRequest updateOrderRequest) {
        Order order = getBy(id);
        order.setCount(updateOrderRequest.getCount());
        return orderConverter.convert(orderRepository.save(order));
    }

    @Override
    public Order getBy(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id", "id", id));
    }

    @Override
    public void delete(Long id) {
        orderRepository.delete(getBy(id));
    }
}
