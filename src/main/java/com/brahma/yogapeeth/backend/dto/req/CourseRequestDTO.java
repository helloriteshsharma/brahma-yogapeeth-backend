package com.brahma.yogapeeth.backend.dto.req;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CourseRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3–100 characters")
    private String title;

    @NotBlank(message = "Short description is required")
    @Size(min = 10, max = 500, message = "Short description must be between 10–500 characters")
    private String shortDescription;

    @NotBlank(message = "Description is required")
    @Size(min = 20, max = 5000, message = "Description must be between 20–5000 characters")
    private String description;

    @NotBlank(message = "Schedule is required")
    private String schedule;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotBlank(message = "Mode is required (Online / Offline / Hybrid)")
    private String mode;

//    @NotBlank(message = "Image URL is required")
//    private String imageUrl;

    @NotNull(message = "Active status must be provided")
    private Boolean active;
}
