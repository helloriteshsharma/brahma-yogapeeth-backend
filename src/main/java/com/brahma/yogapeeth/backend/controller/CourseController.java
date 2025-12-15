package com.brahma.yogapeeth.backend.controller;


import com.brahma.yogapeeth.backend.dto.req.CourseRequestDTO;
import com.brahma.yogapeeth.backend.dto.res.CourseResponseDTO;
import com.brahma.yogapeeth.backend.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.thirdparty.jackson.core.JsonProcessingException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@CrossOrigin
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(
            @RequestPart(value = "course") String courseJson,
            @RequestPart(value = "file") MultipartFile file) throws IOException {

        // Convert JSON string to DTO manually
        ObjectMapper objectMapper = new ObjectMapper();
        CourseRequestDTO courseRequestDTO = objectMapper.readValue(courseJson, CourseRequestDTO.class);

        CourseResponseDTO response = courseService.createCourse(courseRequestDTO, file);
        return ResponseEntity.status(201).body(response);
    }


    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @PathVariable Long id,
            @Valid @RequestPart("course") String courseString,
            @RequestPart(value = "file", required = false) MultipartFile file) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        CourseRequestDTO courseRequestDTO = mapper.readValue(courseString, CourseRequestDTO.class);

        CourseResponseDTO response = courseService.updateCourse(id, courseRequestDTO, file);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
