package com.courseAndStudentManagementSystem.unit;

import com.courseAndStudentManagementSystem.model.Teacher;
import com.courseAndStudentManagementSystem.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherServiceTest extends BaseUnitTest {

    @InjectMocks
    private TeacherService teacherService;
    private Teacher teacher;
    private Teacher existingTeacher;
    private UUID teacherId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teacherId = UUID.randomUUID();
        teacher = createTeacher("Amado Batista", "amado_batista@example.com");
        existingTeacher = createTeacher("Amado Batista", "amado_batista@example.com");
        existingTeacher.setId(teacherId);
    }

    @Test
    void createTeacher() {
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher createdTeacher = teacherService.createTeacher(teacher);

        assertNotNull(createdTeacher);
        assertEquals("Amado Batista", createdTeacher.getName());
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void createTeacher_InvalidEmail() {
        teacher.setEmail("maria@invalid");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            teacherService.createTeacher(teacher);
        });

        assertEquals("Email inválido!", exception.getMessage());
        verify(teacherRepository, times(0)).save(any());
    }

    @Test
    void createTeacher_EmptyName() {
        teacher.setName("");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            teacherService.createTeacher(teacher);
        });

        assertEquals("O nome do professor é obrigatório", exception.getMessage());
        verify(teacherRepository, times(0)).save(any());
    }

    @Test
    void updateTeacher() {
        Teacher updatedTeacher = createTeacher("Amado Batista atualizado", "amado.batista@example.com");

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(existingTeacher));
        when(teacherRepository.save(existingTeacher)).thenReturn(existingTeacher);

        Teacher result = teacherService.updateTeacher(teacherId, updatedTeacher);

        assertNotNull(result);
        assertEquals("Amado Batista atualizado", result.getName());
        verify(teacherRepository, times(1)).findById(teacherId);
        verify(teacherRepository, times(1)).save(existingTeacher);
    }

    @Test
    void updateTeacher_NotFound() {
        Teacher updatedTeacher = createTeacher("Novo Nome", "novo.email@example.com");

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            teacherService.updateTeacher(teacherId, updatedTeacher);
        });

        assertEquals("Professor não encontrado!", exception.getMessage());
        verify(teacherRepository, times(1)).findById(teacherId);
    }

    @Test
    void updateTeacher_InvalidEmail() {
        Teacher updatedTeacher = createTeacher("Maria dos Santos", "maria@invalid");

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(existingTeacher));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            teacherService.updateTeacher(teacherId, updatedTeacher);
        });

        assertEquals("Email inválido!", exception.getMessage());
        verify(teacherRepository, never()).save(any());
    }

    @Test
    void updateTeacher_EmptyName() {
        Teacher updatedTeacher = createTeacher("", "novo.email@example.com");

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(existingTeacher));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            teacherService.updateTeacher(teacherId, updatedTeacher);
        });

        assertEquals("O nome do professor é obrigatório", exception.getMessage());
        verify(teacherRepository, never()).save(any());
    }

    @Test
    void deleteTeacher() {
        doNothing().when(teacherRepository).deleteById(teacherId);
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(existingTeacher));
        teacherService.deleteTeacher(teacherId);
        verify(teacherRepository, times(1)).deleteById(teacherId);
    }

    @Test
    void deleteTeacher_NotFound() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            teacherService.deleteTeacher(teacherId);
        });

        assertEquals("Professor não encontrado!", exception.getMessage());
        verify(teacherRepository, times(0)).deleteById(teacherId);
    }

    @Test
    void findTeacherById() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(existingTeacher));

        Teacher foundTeacher = teacherService.findTeacherById(teacherId);

        assertNotNull(foundTeacher);
        assertEquals(existingTeacher.getName(), foundTeacher.getName());
        assertEquals(existingTeacher.getEmail(), foundTeacher.getEmail());
        verify(teacherRepository, times(1)).findById(teacherId);
    }

    @Test
    void findTeacherById_NotFound() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            teacherService.findTeacherById(teacherId);
        });

        assertEquals("Professor não encontrado!", exception.getMessage());
        verify(teacherRepository, times(1)).findById(teacherId);
    }

    @Test
    void listAllTeachers() {
        List<Teacher> teachers = Arrays.asList(existingTeacher, createTeacher("Carlos Silva", "carlos.silva@example.com"));
        when(teacherRepository.findAll()).thenReturn(teachers);

        List<Teacher> allTeachers = teacherService.findAllTeacher();

        assertNotNull(allTeachers);
        assertEquals(2, allTeachers.size());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void listAllTeachers_EmptyList() {
        when(teacherRepository.findAll()).thenReturn(Arrays.asList());

        List<Teacher> allTeachers = teacherService.findAllTeacher();

        assertNotNull(allTeachers);
        assertTrue(allTeachers.isEmpty());
        verify(teacherRepository, times(1)).findAll();
    }
}
