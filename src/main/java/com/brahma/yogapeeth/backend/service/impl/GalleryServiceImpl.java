package com.brahma.yogapeeth.backend.service.impl;

import com.brahma.yogapeeth.backend.dto.req.GalleryRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.GalleryResponseDTO;
import com.brahma.yogapeeth.backend.entity.Gallery;
import com.brahma.yogapeeth.backend.repository.GalleryRepository;
import com.brahma.yogapeeth.backend.service.AwsS3Service;
import com.brahma.yogapeeth.backend.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final AwsS3Service awsS3Service;

    @Override
    public GalleryResponseDTO createGallery(GalleryRequestDTO requestDTO, MultipartFile image) {

        if (image == null || image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image file is required");
        }

        // Upload new image to S3
        String imageUrl = awsS3Service.uploadFile(image);

        Gallery gallery = Gallery.builder()
                .category(requestDTO.getCategory())
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .build();

        Gallery saved = galleryRepository.save(gallery);
        return mapToDTO(saved);
    }

    @Override
    public List<GalleryResponseDTO> getAllGallery() {
        return galleryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GalleryResponseDTO getGalleryById(Long id) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Gallery image not found"));

        return mapToDTO(gallery);
    }


    @Override
    public GalleryResponseDTO updateGallery(Long id, GalleryRequestDTO requestDTO, MultipartFile image) {

        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Gallery image not found"));


        gallery.setCategory(requestDTO.getCategory());


        if (image != null && !image.isEmpty()) {


            if (gallery.getImageUrl() != null) {
                String oldFileName =
                        gallery.getImageUrl().substring(gallery.getImageUrl().lastIndexOf("/") + 1);
                awsS3Service.deleteFile(oldFileName);
            }

            // Upload new file
            String newImageUrl = awsS3Service.uploadFile(image);
            gallery.setImageUrl(newImageUrl);
        }

        Gallery updated = galleryRepository.save(gallery);
        return mapToDTO(updated);
    }


    @Override
    public void deleteGallery(Long id) {

        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Gallery image not found"));

        // Remove from S3
        if (gallery.getImageUrl() != null) {
            String fileName =
                    gallery.getImageUrl().substring(gallery.getImageUrl().lastIndexOf("/") + 1);
            awsS3Service.deleteFile(fileName);
        }

        galleryRepository.delete(gallery);
    }

    private GalleryResponseDTO mapToDTO(Gallery gallery) {
        return GalleryResponseDTO.builder()
                .id(gallery.getId())
                .category(gallery.getCategory())
                .imageUrl(gallery.getImageUrl())
                .createdAt(gallery.getCreatedAt())
                .build();
    }
}
