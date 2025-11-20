package org.example.onlinestore.repository;

import org.example.onlinestore.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where " +
    "(:name is null or lower(p.name) like lower (concat('%', :name, '%')) ) and" +
    "(:category is null or lower(p.category) = lower(:category)) and " +
    "p.isActive = true ")
    Page<Product> search(@Param("name")String name,
                         @Param("category") String category,
                         Pageable pageable);

    Page<Product> findByIsActiveTrue(Pageable pageable);
}
