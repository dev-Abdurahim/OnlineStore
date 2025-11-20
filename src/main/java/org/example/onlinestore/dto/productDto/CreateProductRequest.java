package org.example.onlinestore.dto.productDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "Mahsulot nomi bo'sh bolish keremas")
    @Size(min = 2, max = 100, message = "Product Nomi 2ta yoki 100 ta belgidan iborat bolish kere ")
    private String name;

    @NotNull(message = "Narxi bosh bolish keremas")
    @PositiveOrZero(message = "Narxi manfiy bomasligi kere")
    private BigDecimal price;

    @NotNull(message = "Stock miqdorini kiritish shart")
    @Min(value = 0, message = "Stock 0 dan kam bo'lmasligi kerak")
    private Integer stock;

    private String category;


}
