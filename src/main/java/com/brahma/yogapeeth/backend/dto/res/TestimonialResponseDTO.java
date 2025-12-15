package com.brahma.yogapeeth.backend.dto.res;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TestimonialResponseDTO {

    private Long id;
    private String name;
    private String feedback;
    private Integer rating;

    private String photoUrl;

    private LocalDateTime createdAt;
}
