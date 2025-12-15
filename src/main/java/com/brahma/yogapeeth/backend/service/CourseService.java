package com.brahma.yogapeeth.backend.service;

import com.brahma.yogapeeth.backend.dto.req.CourseRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.CourseResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {

    CourseResponseDTO createCourse(CourseRequestDTO courseRequestDTO, MultipartFile file);

    List<CourseResponseDTO> getAllCourses();

    CourseResponseDTO getCourseById(Long id);

    CourseResponseDTO updateCourse(Long id, CourseRequestDTO courseRequestDTO, MultipartFile file);

    void deleteCourse(Long id);
}
