package az.nicat.shoppingapp.repository;

import az.nicat.shoppingapp.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByName(String name);
}