package org.example.onlinestore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.onlinestore.dto.orderDto.CreateOrderRequest;
import org.example.onlinestore.dto.orderDto.OrderResponse;
import org.example.onlinestore.dto.orderDto.UpdateOrderStatusRequest;
import org.example.onlinestore.dto.orderItemDto.OrderItemRequest;
import org.example.onlinestore.dto.orderItemDto.OrderItemResponse;
import org.example.onlinestore.exception.customexception.InsufficientStockException;
import org.example.onlinestore.exception.customexception.InvalidOrderStatusException;
import org.example.onlinestore.exception.customexception.OrderNotFoundException;
import org.example.onlinestore.exception.globalexception.BusinessException;
import org.example.onlinestore.model.Order;
import org.example.onlinestore.model.OrderItem;
import org.example.onlinestore.model.OrderStatus;
import org.example.onlinestore.model.Product;
import org.example.onlinestore.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public List<OrderResponse> getOrdersByCustomerEmail(String email){
        return orderRepository.findByCustomerEmail(email).stream()
                .map(this::toOrderResponse)
                .toList();
    }

    public List<OrderResponse> getAllOrders(){
        return orderRepository.findAll().stream()
                .map(this::toOrderResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long id){
        Order order = findOrderById(id);
        return toOrderResponse(order);
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request){
        log.info("Yangi buyurtma yaratilmoqda: {}", request.getCustomerEmail() + " tomonidan");

        Set<Long> productsIds = new HashSet<>();
        for (OrderItemRequest item : request.getItems()){
            if(!productsIds.add(item.getProductId())){
                throw new BusinessException("Bir buyurtmada bir xil mahsulot ikki marta bo'lmasligi kerak!");
            }
        }
        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .status(OrderStatus.PENDING)
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for(OrderItemRequest itemReq : request.getItems()){
            Product product = productService.findProductById(itemReq.getProductId());

            if(product.getStock() < itemReq.getQuantity()){
                throw new InsufficientStockException(product.getName(), itemReq.getQuantity(), product.getStock());
            }

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .unitPrice(product.getPrice())
                    .totalPrice(itemTotal)
                    .build();
            order.getOrderItems().add(orderItem);
        }
        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);

        for(OrderItem item : order.getOrderItems()){
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
        }
        log.info("Buyurtma muvaffaqiyatli yaratildi: ID = {}", order.getId());
        return toOrderResponse(order);


    }

    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        Order order = findOrderById(orderId);

        if (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new InvalidOrderStatusException(orderId, order.getStatus().name(), request.getOrderStatus().name());
        }

        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED && order.getStatus() != OrderStatus.SHIPPED) {
            throw new InvalidOrderStatusException("Buyurtma faqat PENDING, CONFIRMED yoki SHIPPED holatida o'zgartirilishi mumkin");
        }

        OrderStatus newStatus = request.getOrderStatus();

        if (newStatus == OrderStatus.CANCELLED) {
            returnStock(order);
        }
        order.setStatus(newStatus);
        log.info("Buyurtma {} statusi {} ga o'zgartirildi", orderId, newStatus);
        return toOrderResponse(order);
    }

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = findOrderById(orderId);
        if(order.getStatus() == OrderStatus.DELIVERED){
            throw new InvalidOrderStatusException("Yetkazib berilgan buyurtma bekor qilinmaydi!");
        }
        returnStock(order);
        order.setStatus(OrderStatus.CANCELLED);
        log.info("Buyurtma {} bekor qilindi va stock qaytarildi", orderId);

    }

    private void returnStock(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
        }
    }

    private OrderResponse toOrderResponse(Order order) {
            List<OrderItemResponse> items = order.getOrderItems().stream()
                    .map(item -> OrderItemResponse.builder()
                            .productId(item.getProduct().getId())
                            .productName(item.getProduct().getName())
                            .quantity(item.getQuantity())
                            .unitPrice(item.getUnitPrice())
                            .totalPrice(item.getTotalPrice())
                            .build())
                    .toList();

            return OrderResponse.builder()
                    .id(order.getId())
                    .customerName(order.getCustomerName())
                    .customerEmail(order.getCustomerEmail())
                    .orderDate(order.getOrderDate())
                    .status(order.getStatus())
                    .totalAmount(order.getTotalAmount())
                    .items(items)
                    .build();
    }

    public Order findOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }


}
