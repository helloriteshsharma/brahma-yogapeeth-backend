package com.brahma.yogapeeth.backend.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnrollmentPaymentDTO {

    @NotBlank(message = "Payment status is required")
    private String paymentStatus; // e.g., PAID, FAILED
}
