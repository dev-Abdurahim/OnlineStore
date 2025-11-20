package org.example.onlinestore.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.onlinestore.dto.productDto.CreateProductRequest;
import org.example.onlinestore.dto.productDto.ProductResponse;
import org.example.onlinestore.exception.customexception.ProductNotFoundException;
import org.example.onlinestore.model.Product;
import org.example.onlinestore.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductResponse> getAllProducts(Pageable pageable){
        return productRepository.findByIsActiveTrue(pageable)
                .map(this::toProductResponse);
    }



    public Page<ProductResponse> searchProducts(String name, String category, Pageable pageable){
        return productRepository.search(name, category, pageable)
                .map(this::toProductResponse);
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request){
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .category(request.getCategory())
                .stock(request.getStock())
                .isActive(true)
                .build();
        product = productRepository.save(product);
        log.info("Product muvaffaqiyatli yaratildi: ID = {}",product.getId());

        return toProductResponse(product);
    }
    @Transactional
    public ProductResponse updateProduct(Long id, CreateProductRequest request){
        Product product = findProductById(id);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(request.getCategory());

        product = productRepository.save(product);
        return toProductResponse(product);
    }

    @Transactional
    public void deleteProduct(Long id){
        Product product = findProductById(id);
        product.setIsActive(false);
        productRepository.save(product);
    }

    public ProductResponse getProductById(Long id){
      Product product = findProductById(id);
      return toProductResponse(product);
    }

    public Product findProductById(Long id){
        return productRepository.findById(id)
                .filter(Product::getIsActive)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .isActive(product.getIsActive())
                .createdAt(product.getCreatedAt())
                .build();
    }


}
