package com.brahma.yogapeeth.backend.dto.res;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class GalleryResponseDTO {

    private Long id;
    private String imageUrl;
    private String category;
    private LocalDateTime createdAt;
}
