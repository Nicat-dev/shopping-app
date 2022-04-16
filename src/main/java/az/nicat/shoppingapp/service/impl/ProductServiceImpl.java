package az.nicat.shoppingapp.service.impl;

import az.nicat.shoppingapp.converter.ProductConverter;
import az.nicat.shoppingapp.exception.ResourceExistsException;
import az.nicat.shoppingapp.exception.ResourceNotFoundException;
import az.nicat.shoppingapp.model.dto.ProductDto;
import az.nicat.shoppingapp.model.entity.Product;
import az.nicat.shoppingapp.model.request.CreateProductRequest;
import az.nicat.shoppingapp.model.request.UpdateProductRequest;
import az.nicat.shoppingapp.repository.ProductRepository;
import az.nicat.shoppingapp.service.ProductImageService;
import az.nicat.shoppingapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final ProductImageService productImageService;

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    @Transactional
    public ProductDto create(CreateProductRequest createProductRequest, MultipartFile[] multipartFiles) {
        log.info("Creating product: {} and image files: {}",createProductRequest,multipartFiles.length);
        Product product = Product.builder()
                .name(createProductRequest.getName())
                .description(createProductRequest.getDescription())
                .price(createProductRequest.getPrice())
                .stock(createProductRequest.getStock())
                .build();
        Product savedPraduct = productRepository.saveAndFlush(product);
        productImageService.create(savedPraduct,multipartFiles);
        return productConverter.convert(savedPraduct);
    }

    @Override
    public ProductDto update(Long id, UpdateProductRequest updateProductRequest) {
        Product product = getBy(id);
        if (!product.getName().equals(updateProductRequest.getName())){
            doesExistsById(updateProductRequest.getName());
            product.setName(updateProductRequest.getName());
        }
        return productConverter.convert(productRepository.saveAndFlush(product));
    }

    @Override
    public List<ProductDto> getAll() {
        log.info("All of product fetching..." );
        return productConverter.convert(productRepository.findAll());
    }

    @Override
    public ProductDto getById(Long id) {
        return productConverter.convert(getBy(id));
    }

    @Override
    public Product getBy(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("id","id",id));
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(getBy(id));
    }

    private void doesExistsById(String name){
        if (productRepository.existsByName(name))
            throw new ResourceExistsException("name","name",name);
    }
}
