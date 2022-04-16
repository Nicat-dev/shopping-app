package az.nicat.shoppingapp.service.impl;

import az.nicat.shoppingapp.model.dto.ProductImageDto;
import az.nicat.shoppingapp.model.entity.Product;
import az.nicat.shoppingapp.model.entity.ProductImage;
import az.nicat.shoppingapp.repository.ProductImageRepository;
import az.nicat.shoppingapp.service.ImageFileService;
import az.nicat.shoppingapp.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ImageFileService imageFileService;

    @Override
    public void create(Product product, MultipartFile[] multipartFiles) {

        List<ProductImage> productImages = stream(multipartFiles).map(multipartFile -> {
                    String path = imageFileService.add(multipartFile);
                    return ProductImage.builder()
                            .path(path)
                            .product(product)
                            .build();
                })
                .collect(Collectors.toList());
        productImageRepository.saveAll(productImages);
    }
}
