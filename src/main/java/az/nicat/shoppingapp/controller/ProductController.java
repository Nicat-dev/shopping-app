package az.nicat.shoppingapp.controller;

import az.nicat.shoppingapp.model.dto.ProductDto;
import az.nicat.shoppingapp.model.request.CreateProductRequest;
import az.nicat.shoppingapp.model.response.BaseResponse;
import az.nicat.shoppingapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse<ProductDto>> createNewProduct(@RequestBody @Valid CreateProductRequest request,
                                                                     MultipartFile[] multipartFiles){
        ProductDto productDto =  productService.create(request,multipartFiles);
        return ResponseEntity.ok(new BaseResponse<>(Boolean.TRUE,"Product successfully added",productDto));

    }
}
