package az.nicat.shoppingapp.repository;

import az.nicat.shoppingapp.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}