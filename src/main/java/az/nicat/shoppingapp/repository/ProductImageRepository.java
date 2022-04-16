package az.nicat.shoppingapp.repository;

import az.nicat.shoppingapp.model.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}