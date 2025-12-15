package com.brahma.yogapeeth.backend.dto.req;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TestimonialRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2–100 characters")
    private String name;

    @NotBlank(message = "Feedback is required")
    @Size(min = 10, max = 1000, message = "Feedback must be between 10–1000 characters")
    private String feedback;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private Integer rating;


}
