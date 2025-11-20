package org.example.onlinestore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.onlinestore.dto.orderDto.CreateOrderRequest;
import org.example.onlinestore.dto.orderDto.OrderResponse;
import org.example.onlinestore.dto.orderDto.UpdateOrderStatusRequest;
import org.example.onlinestore.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/customer/{email}")
    public List<OrderResponse> getOrdersByCustomerEmail(@PathVariable String email){
        return orderService.getOrdersByCustomerEmail(email);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders(){
       return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request){
        return orderService.createOrder(request);
    }

    @PutMapping("/{id}/status")
    public OrderResponse updateOrderStatus( @PathVariable Long id, @Valid @RequestBody UpdateOrderStatusRequest request){
        return orderService.updateOrderStatus(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable Long id){
        orderService.cancelOrder(id);

    }




}
