package com.brahma.yogapeeth.backend.service;



import com.brahma.yogapeeth.backend.dto.req.GalleryRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.GalleryResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GalleryService {

    GalleryResponseDTO createGallery(GalleryRequestDTO requestDTO, MultipartFile image);

    List<GalleryResponseDTO> getAllGallery();

    GalleryResponseDTO getGalleryById(Long id);

    GalleryResponseDTO updateGallery(Long id, GalleryRequestDTO requestDTO, MultipartFile image);

    void deleteGallery(Long id);
}
