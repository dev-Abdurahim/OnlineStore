package org.example.onlinestore.service;

import org.example.onlinestore.dto.productDto.CreateProductRequest;
import org.example.onlinestore.dto.productDto.ProductResponse;
import org.example.onlinestore.exception.customexception.ProductNotFoundException;
import org.example.onlinestore.model.Product;
import org.example.onlinestore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private  ProductService productService;

    @Autowired
    private  ProductRepository productRepository;
    @Test
    void shouldCreateAndFindProduct(){
        var request = new CreateProductRequest("Test Phone", new BigDecimal("800.00"),10,"Electronics");
        ProductResponse created = productService.createProduct(request);
        assertNotNull(created.getId());
        assertEquals("Test Phone", created.getName());
        assertEquals(new BigDecimal("800.00"),created.getPrice());

        ProductResponse found = productService.getProductById(created.getId());
        assertEquals("Test Phone",found.getName());

    }

    @Test
    void SearchByNameAndCategory(){
        productRepository.save(Product.builder()
                        .name("iPhone 17 pro max")
                        .price(new BigDecimal("1000.00"))
                        .stock(20)
                        .category("Electronics")
                        .isActive(true)
                        .build());
        Page<ProductResponse> result = productService.searchProducts(
                "iphone",
                "Electronics",
                PageRequest.of(0,10));

        assertFalse(result.isEmpty());
        assertTrue(result.getContent().get(0).getName().toLowerCase().contains("iphone"));
    }

    @Test
    void SoftDeleteProduct(){
        Product product = productRepository.save(Product.builder()
                        .name("MacBook Pro")
                        .price(new BigDecimal("990.00"))
                        .stock(1)
                        .isActive(true)
                        .build());
        productService.deleteProduct(product.getId());
        assertThrows(ProductNotFoundException.class, () ->
                productService.findProductById(product.getId()));
    }

}
