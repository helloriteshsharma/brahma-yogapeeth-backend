package com.brahma.yogapeeth.backend.dto.req;

import jakarta.validation.constraints.*;
import lombok.Data;



@Data
public class GalleryRequestDTO {

    @NotBlank(message = "Category is required")
    @Size(min = 3, max = 100, message = "Category must be between 3–100 characters")
    private String category;
}
