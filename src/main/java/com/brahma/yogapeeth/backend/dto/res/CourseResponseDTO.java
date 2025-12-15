package com.brahma.yogapeeth.backend.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseResponseDTO {

    private Long id;
    private String title;
    private String shortDescription;
    private String description;

    private String schedule;
    private Double price;

    private String mode;

    private String imageUrl;

    private Boolean active;
}
