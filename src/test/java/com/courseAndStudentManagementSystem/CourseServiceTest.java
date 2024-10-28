package com.courseAndStudentManagementSystem;

import com.courseAndStudentManagementSystem.model.Course;
import com.courseAndStudentManagementSystem.model.Teacher;
import com.courseAndStudentManagementSystem.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest extends BaseTest {

    @InjectMocks
    private CourseService courseService;
    private Course course;
    private Course existingCourse;
    private UUID courseId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseId = UUID.randomUUID();
        course = createCourse("Analise e desenvolvimento de sistemas", "ADS");
        existingCourse = createCourse("Analise e desenvolvimento de sistemas", "ADS");
        existingCourse.setCourseId(courseId);
    }

    @Test
    void createCourse() {
        when(courseRepository.save(course)).thenReturn(course);

        Course createdCourse = courseService.createCourse(course);

        assertNotNull(createdCourse);
        assertEquals("Analise e desenvolvimento de sistemas", createdCourse.getName());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void createCourse_EmptyName() {
        course.setName("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.createCourse(course);
        });

        assertEquals("O nome do curso é obrigatório!", exception.getMessage());
        verify(courseRepository, times(0)).save(any());
    }

    @Test
    void createCourse_NonExistentProfessor() {
        Teacher teacher = createTeacher("Amado Batista", "amado.batista@example.com");
        teacher.setId(UUID.randomUUID());
        course.setTeacher(teacher);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            courseService.createCourse(course);
        });

        assertEquals("Professor não encontrado!", exception.getMessage());
        verify(courseRepository, times(0)).save(any());
    }

    @Test
    void createCourse_DateInvalid() {
        Teacher teacher = createTeacher("Amado Batista", "amado.batista@example.com");
        teacher.setId(UUID.randomUUID());
        course.setTeacher(teacher);

        Instant initialDate = Instant.parse("2024-10-28T00:00:00Z");
        Instant finalDate = Instant.parse("2024-10-27T00:00:00Z");

        course.setInitialDate(initialDate);
        course.setFinalDate(finalDate);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.createCourse(course);
        });

        assertEquals("A data inicial não pode ser posterior à data final.", exception.getMessage());
        verify(courseRepository, times(0)).save(any());
    }

    @Test
    void updateCourse() {
        Course updatedCourse = createCourse("Analise e desenvolvimento de sistemas Senai", "ADS");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(existingCourse)).thenReturn(existingCourse);

        Course result = courseService.updateCourse(courseId, updatedCourse);

        assertNotNull(result);
        assertEquals("Analise e desenvolvimento de sistemas Senai", result.getName());
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(existingCourse);
    }

    @Test
    void updateCourse_NotFound() {
        Course updatedCourse = createCourse("Novo Nome", "Nova Descrição");

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            courseService.updateCourse(courseId, updatedCourse);
        });

        assertEquals("Curso não encontrado!", exception.getMessage());
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(0)).save(any());
    }

    @Test
    void deleteCourse() {
        doNothing().when(courseRepository).deleteById(courseId);

        courseService.deleteCourse(courseId);

        verify(courseRepository, times(1)).deleteById(courseId);
    }

    @Test
    void findCourseById() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));

        Course foundCourse = courseService.findCourseById(courseId);

        assertNotNull(foundCourse);
        assertEquals(existingCourse.getName(), foundCourse.getName());
        assertEquals(existingCourse.getDescription(), foundCourse.getDescription());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void findCourseById_NotFound() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            courseService.findCourseById(courseId);
        });

        assertEquals("Curso não encontrado!", exception.getMessage());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void findAllCourses() {
        when(courseRepository.findAll()).thenReturn(Collections.singletonList(existingCourse));

        List<Course> courses = courseService.findAllCourses();

        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals(existingCourse.getName(), courses.get(0).getName());
        verify(courseRepository, times(1)).findAll();
    }
}
