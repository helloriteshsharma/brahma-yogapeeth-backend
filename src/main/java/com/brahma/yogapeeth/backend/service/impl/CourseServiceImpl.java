package com.brahma.yogapeeth.backend.service.impl;

import com.brahma.yogapeeth.backend.dto.req.CourseRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.CourseResponseDTO;
import com.brahma.yogapeeth.backend.entity.Course;
import com.brahma.yogapeeth.backend.repository.CourseRepository;
import com.brahma.yogapeeth.backend.service.AwsS3Service;
import com.brahma.yogapeeth.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final AwsS3Service awsS3Service;

    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO courseRequestDTO, MultipartFile file) {
        String imageUrl = awsS3Service.uploadFile(file);

        Course course = Course.builder()
                .title(courseRequestDTO.getTitle())
                .shortDescription(courseRequestDTO.getShortDescription())
                .description(courseRequestDTO.getDescription())
                .schedule(courseRequestDTO.getSchedule())
                .price(courseRequestDTO.getPrice())
                .mode(courseRequestDTO.getMode())
                .imageUrl(imageUrl)
                .active(courseRequestDTO.getActive())
                .build();

        Course saved = courseRepository.save(course);

        return mapToResponseDTO(saved);
    }

    @Override
    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        return mapToResponseDTO(course);
    }

    @Override
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO courseRequestDTO, MultipartFile file) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        course.setTitle(courseRequestDTO.getTitle());
        course.setShortDescription(courseRequestDTO.getShortDescription());
        course.setDescription(courseRequestDTO.getDescription());
        course.setSchedule(courseRequestDTO.getSchedule());
        course.setPrice(courseRequestDTO.getPrice());
        course.setMode(courseRequestDTO.getMode());
        course.setActive(courseRequestDTO.getActive());

        if (file != null && !file.isEmpty()) {
            // Delete old file if needed (extract filename from URL)
            if (course.getImageUrl() != null && !course.getImageUrl().isEmpty()) {
                String oldFileName = course.getImageUrl().substring(course.getImageUrl().lastIndexOf("/") + 1);
                awsS3Service.deleteFile(oldFileName);
            }
            String newImageUrl = awsS3Service.uploadFile(file);
            course.setImageUrl(newImageUrl);
        }

        Course updated = courseRepository.save(course);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        if (course.getImageUrl() != null && !course.getImageUrl().isEmpty()) {
            String fileName = course.getImageUrl().substring(course.getImageUrl().lastIndexOf("/") + 1);
            awsS3Service.deleteFile(fileName);
        }

        courseRepository.delete(course);
    }

    private CourseResponseDTO mapToResponseDTO(Course course) {
        return CourseResponseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .shortDescription(course.getShortDescription())
                .description(course.getDescription())
                .schedule(course.getSchedule())
                .price(course.getPrice())
                .mode(course.getMode())
                .imageUrl(course.getImageUrl())
                .active(course.getActive())
                .build();
    }
}
