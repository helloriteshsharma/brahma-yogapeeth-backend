package com.brahma.yogapeeth.backend.dto.req;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccommodationRequestDTO {
    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Price is required")
    private Double price;
}
