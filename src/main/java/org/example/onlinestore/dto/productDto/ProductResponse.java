package org.example.onlinestore.dto.productDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private boolean isActive;
    private LocalDateTime createdAt;


}
