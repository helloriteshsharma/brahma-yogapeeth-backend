package com.brahma.yogapeeth.backend.dto.req;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EnquiryRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3–100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Phone must be 7–15 digits")
    private String phone;

    @NotBlank(message = "Message is required")
    @Size(min = 10, max = 2000, message = "Message must be between 10–2000 characters")
    private String message;
}
