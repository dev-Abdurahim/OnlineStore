package org.example.onlinestore.repository;

import org.example.onlinestore.model.Order;
import org.example.onlinestore.model.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldFindByCustomerEmail() {
        Order order = Order.builder()
                .customerName("Ali")
                .customerEmail("ali@gmail.com")
                .status(OrderStatus.PENDING)
                .build();
        orderRepository.save(order);

        var result = orderRepository.findByCustomerEmail("ali@gmail.com");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCustomerName()).isEqualTo("Ali");
    }


}
