package org.example.onlinestore.dto.orderItemDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {

    @NotNull(message = "Product ID bo'sh bo'lmasligi kerak")
    private Long ProductId;

    @NotNull(message = "Miqdor kiritilishi shart")
    @Min(value = 1, message = "Miqdor kamida 1 bo'lishi kerak")
    private Integer quantity;



}
