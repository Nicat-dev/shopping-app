package az.nicat.shoppingapp.converter;

import az.nicat.shoppingapp.model.dto.ProductDto;
import az.nicat.shoppingapp.model.dto.ProductImageDto;
import az.nicat.shoppingapp.model.entity.Product;
import az.nicat.shoppingapp.model.entity.ProductImage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter extends BaseConverter<Product, ProductDto> {
    @Override
    public ProductDto convert(Product from) {
        return new ProductDto(
                from.getId(),
                from.getName(),
                from.getPrice(),
                from.getStock(),
                from.getDescription(),
                getProductImages(from.getProductImages()),
                from.getCreatedAt(),
                from.getUpdatedAt()
        );
    }

    private List<ProductImageDto> getProductImages(List<ProductImage> productImages) {
        if (productImages == null) return List.of();
        return productImages.stream()
                .map(productImage -> new ProductImageDto(
                        productImage.getId(),
                        productImage.getProduct().getId(),
                        productImage.getPath(),
                        productImage.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> convert(List<Product> from) {
        if (from == null) return List.of();
        return from.stream().map(this::convert).collect(Collectors.toList());
    }
}
