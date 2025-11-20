package org.example.onlinestore.dto.orderDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinestore.model.OrderStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequest {

    @NotNull(message = "Yangi status kiritilishi shart")
    private OrderStatus orderStatus;
}
