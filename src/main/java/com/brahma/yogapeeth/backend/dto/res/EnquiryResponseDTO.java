package com.brahma.yogapeeth.backend.dto.res;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class EnquiryResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String message;
    private LocalDateTime createdAt;
    private String status; // Pending / Contacted
}
