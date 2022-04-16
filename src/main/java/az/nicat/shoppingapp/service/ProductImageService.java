package az.nicat.shoppingapp.service;

import az.nicat.shoppingapp.model.dto.ProductImageDto;
import az.nicat.shoppingapp.model.entity.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImageService {
    void create(Product product, MultipartFile[] multipartFiles);
}
