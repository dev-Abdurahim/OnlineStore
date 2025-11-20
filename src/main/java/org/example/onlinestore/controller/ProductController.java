package org.example.onlinestore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.onlinestore.dto.productDto.CreateProductRequest;
import org.example.onlinestore.dto.productDto.ProductResponse;
import org.example.onlinestore.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductResponse> getAllProducts(Pageable pageable){
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/search")
    public Page<ProductResponse> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            Pageable pageable){
        return productService.searchProducts(name, category, pageable);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody CreateProductRequest request){
        return productService.createProduct(request);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody CreateProductRequest request){
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

}
