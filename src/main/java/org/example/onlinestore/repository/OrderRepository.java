package org.example.onlinestore.repository;

import org.example.onlinestore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerEmail(String customerEmail);
}
