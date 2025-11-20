package org.example.onlinestore.repository;

import org.example.onlinestore.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldFindByIsActiveTrue() {
        productRepository.save(Product.builder().name("Test").price(new BigDecimal("100")).stock(10).isActive(true).build());
        productRepository.save(Product.builder().name("Deleted").price(new BigDecimal("200")).stock(5).isActive(false).build());
        var result = productRepository.findByIsActiveTrue(PageRequest.of(1, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Test");
    }


    @Test
    void shouldReturnFirstPageWith10Products() {
        var page = productRepository.findByIsActiveTrue(PageRequest.of(0, 10));

        assertThat(page.getContent()).hasSize(10);
        assertThat(page.getTotalElements()).isEqualTo(10);
    }

    @Test
    void SecondPage() {
        var page = productRepository.findByIsActiveTrue(PageRequest.of(1, 10));

        assertThat(page.getContent()).isEmpty();
        assertThat(page.getTotalPages()).isEqualTo(1);
    }

    @Test
    void SearchByName() {
        var page = productRepository.search("iphone", null, PageRequest.of(0, 10));

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getName()).isEqualTo("iPhone 15 Pro");
    }

    @Test
    void SearchByCategory() {
        var page = productRepository.search(null, "electronics", PageRequest.of(0, 10));

        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent().get(0).getCategory()).isEqualTo("Electronics");
    }

    @Test
    void SearchByNameAndCategory() {
        var page = productRepository.search("samsung", "electronics", PageRequest.of(0, 10));

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getName()).isEqualTo("Samsung Galaxy S24");
    }

    @Test
    void ReturnEmptyWhenNoMatch() {
        var page = productRepository.search("product", "nothing", PageRequest.of(0, 10));
        assertThat(page.getContent()).isEmpty();
    }

    @Test
    void ReturnEmptyWhenSearchingInactiveProduct() {

        productRepository.save(Product.builder()
                .name("Deleted Product")
                .price(new java.math.BigDecimal("999"))
                .stock(5)
                .category("Test")
                .isActive(false)
                .build());

        var page = productRepository.search("deleted", null, PageRequest.of(0, 10));
        assertThat(page.getContent()).isEmpty();
    }
}
