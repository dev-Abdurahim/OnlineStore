package org.example.onlinestore.service;

import org.example.onlinestore.dto.orderDto.CreateOrderRequest;
import org.example.onlinestore.dto.orderDto.OrderResponse;
import org.example.onlinestore.dto.orderItemDto.OrderItemRequest;
import org.example.onlinestore.dto.productDto.CreateProductRequest;
import org.example.onlinestore.exception.customexception.InsufficientStockException;
import org.example.onlinestore.exception.globalexception.BusinessException;
import org.example.onlinestore.model.OrderStatus;
import org.example.onlinestore.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    private Long createTestProduct(){
        var req = new CreateProductRequest(
                "MacBookPro", new BigDecimal("100.00"),10,"Electronics");
        return productService.createProduct(req).getId();
    }

    @Test
    void shouldCreateOrderAndReduceStock(){
        Long productId = createTestProduct();

        var request = new CreateOrderRequest(
                "Ali", "ali@test.com",
                List.of(new OrderItemRequest(productId, 3))
        );
        OrderResponse order = orderService.createOrder(request);
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(new BigDecimal("300.00"), order.getTotalAmount());

        Product product = productService.findProductById(productId);
        assertEquals(7,product.getStock());
    }

    @Test
    void ThrowInsufficientStock(){
        Long productId = createTestProduct();
        var request = new CreateOrderRequest("A","A@a.com",
                List.of(new OrderItemRequest(productId,15)));

        assertThrows(InsufficientStockException.class, () ->
                orderService.createOrder(request));
    }

    @Test
    void CancelOrderAndReturnStock(){
        Long productId = createTestProduct();
        var orderReq = new CreateOrderRequest(
                "A","A@a.com",
                List.of(new OrderItemRequest(productId,4)));
        OrderResponse order = orderService.createOrder(orderReq);

        orderService.cancelOrder(order.getId());
        Product product = productService.findProductById(productId);
        assertEquals(10, product.getStock());

    }

    @Test
    void shouldNotAllowDuplicateProductsInOneOrder(){
        Long productId = createTestProduct();
        var request = new CreateOrderRequest("C","C@c.com",List.of(
               new OrderItemRequest(productId,1),
                new OrderItemRequest(productId,2)
        ));

        assertThrows(BusinessException.class, () ->
                orderService.createOrder(request));
    }



}
