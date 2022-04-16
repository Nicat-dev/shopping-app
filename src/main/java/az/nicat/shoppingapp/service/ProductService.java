package az.nicat.shoppingapp.service;

import az.nicat.shoppingapp.model.dto.ProductDto;
import az.nicat.shoppingapp.model.entity.Product;
import az.nicat.shoppingapp.model.request.CreateProductRequest;
import az.nicat.shoppingapp.model.request.UpdateProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDto create(CreateProductRequest createProductRequest, MultipartFile[] multipartFiles);
    ProductDto update(Long id, UpdateProductRequest updateProductRequest);
    List<ProductDto> getAll();
    ProductDto getById(Long id);
    Product getBy(Long id);
    void delete(Long id);
}
