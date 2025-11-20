package org.example.onlinestore.dto.orderDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinestore.dto.orderItemDto.OrderItemRequest;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotBlank(message = "Mijoz ismi bosh bomasligi kerak")
    private String customerName;

    @NotBlank(message = "Email bosh bomasiligi kerak")
    @Email(message = "Email formati notogir")
    private String customerEmail;

    @NotEmpty(message = "Buyurtmada kamida bitta mahsulot bo'lishi kerak")
    private List<OrderItemRequest> items;
}
