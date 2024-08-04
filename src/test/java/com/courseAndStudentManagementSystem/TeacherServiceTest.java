package com.courseAndStudentManagementSystem;

import com.courseAndStudentManagementSystem.model.Teacher;
import com.courseAndStudentManagementSystem.repository.TeacherRepository;
import com.courseAndStudentManagementSystem.service.TeacherService;
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

public class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("Amado Batista");
        teacher.setEmail("amado_batista@example.com");

        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher createdTeacher = teacherService.createTeacher(teacher);

        assertNotNull(createdTeacher);
        assertEquals("Amado Batista", createdTeacher.getName());
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void updateTeacher() {
        UUID teacherId = UUID.randomUUID();
        Teacher existingTeacher = new Teacher();
        existingTeacher.setId(teacherId);
        existingTeacher.setName("Amado Batista");
        existingTeacher.setEmail("amado_batista@example.com");

        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setName("Amado Batista atualizado");
        updatedTeacher.setEmail("amado_batista@example.com");

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(existingTeacher));
        when(teacherRepository.save(existingTeacher)).thenReturn(existingTeacher);

        Teacher result = teacherService.updateTeacher(teacherId, updatedTeacher);

        assertNotNull(result);
        assertEquals("Amado Batista atualizado", result.getName());
        verify(teacherRepository, times(1)).findById(teacherId);
        verify(teacherRepository, times(1)).save(existingTeacher);
    }

    @Test
    void deleteTeacher() {
        UUID teacherId = UUID.randomUUID();
        doNothing().when(teacherRepository).deleteById(teacherId);

        teacherService.deleteTeacher(teacherId);

        verify(teacherRepository, times(1)).deleteById(teacherId);
    }
}
