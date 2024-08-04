package com.courseAndStudentManagementSystem;

import com.courseAndStudentManagementSystem.model.Course;
import com.courseAndStudentManagementSystem.repository.CourseRepository;
import com.courseAndStudentManagementSystem.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCourse() {
        Course course = new Course();
        course.setName("Analise e desenvolvimento de sistemas");
        course.setDescription("ADS");

        when(courseRepository.save(course)).thenReturn(course);

        Course createdCourse = courseService.createCourse(course);

        assertNotNull(createdCourse);
        assertEquals("Analise e desenvolvimento de sistemas", createdCourse.getName());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void updateCourse() {
        UUID courseId = UUID.randomUUID();
        Course existingCourse = new Course();
        existingCourse.setCourseId(courseId);
        existingCourse.setName("Analise e desenvolvimento de sistemas");
        existingCourse.setDescription("ADS");

        Course updatedCourse = new Course();
        updatedCourse.setName("Analise e desenvolvimento de sistemas Senai");
        updatedCourse.setDescription("ADS");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(existingCourse)).thenReturn(existingCourse);

        Course result = courseService.updateCourse(courseId, updatedCourse);

        assertNotNull(result);
        assertEquals("Analise e desenvolvimento de sistemas Senai", result.getName());
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(existingCourse);
    }

    @Test
    void deleteCourse() {
        UUID courseId = UUID.randomUUID();
        doNothing().when(courseRepository).deleteById(courseId);

        courseService.deleteCourse(courseId);

        verify(courseRepository, times(1)).deleteById(courseId);
    }
}
