package com.courseAndStudentManagementSystem.unit;

import com.courseAndStudentManagementSystem.model.Student;
import com.courseAndStudentManagementSystem.service.StudentService;
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

public class StudentServiceTest extends BaseUnitTest {

    @InjectMocks
    private StudentService studentService;
    private Student student;
    private Student existingStudent;
    private UUID studentId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentId = UUID.randomUUID();
        student = createStudent("Reginaldo Rossi", "reginaldo_rossi@example.com");
        existingStudent = createStudent("Reginaldo Rossi", "reginaldo_rossi@example.com");
        existingStudent.setId(studentId);
    }

    @Test
    void createStudent() {
        when(studentRepository.save(student)).thenReturn(student);
        Student createdStudent = studentService.createStudent(student);
        assertNotNull(createdStudent);
        assertEquals("Reginaldo Rossi", createdStudent.getName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void createStudent_InvalidEmail() {
        student.setEmail("email_invalido#email.com");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            studentService.createStudent(student);
        });

        assertEquals("Email inválido!", exception.getMessage());

        verify(studentRepository, times(0)).save(any());
    }

    @Test
    void createStudent_EmptyName() {
        student.setName("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            studentService.createStudent(student);
        });

        assertEquals("O nome do estudante é obrigatório!", exception.getMessage());
        verify(studentRepository, times(0)).save(any());
    }

    @Test
    void updateStudent() {
        Student updatedStudent = createStudent("Reginaldo Rossi atualizado", "reginaldo.rossi@example.com");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);

        Student result = studentService.updateStudent(studentId, updatedStudent);
        assertNotNull(result);
        assertEquals("Reginaldo Rossi atualizado", result.getName());
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    void updateStudent_NotFound() {
        Student updatedStudent = createStudent("Novo Nome", "novo.email@example.com");

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(studentId, updatedStudent);
        });

        assertEquals("Estudante não encontrado!", exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void updateStudent_InvalidEmail() {
        Student updatedStudent = createStudent("Reginaldo Rossi atualizado", "email_invalido%$example.com");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(studentId, updatedStudent);
        });

        assertEquals("Email inválido!", exception.getMessage());
        verify(studentRepository, never()).save(existingStudent);
    }

    @Test
    void updateStudent_EmptyName() {
        Student updatedStudent = createStudent("", "novo.email@example.com");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(studentId, updatedStudent);
        });

        assertEquals("O nome do estudante é obrigatório!", exception.getMessage());
        verify(studentRepository, never()).save(any());
    }

    @Test
    void deleteStudent() {
        doNothing().when(studentRepository).deleteById(studentId);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        studentService.deleteStudent(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void deleteStudent_NotFound() {
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.deleteStudent(studentId);
        });

        assertEquals("Estudante não encontrado!", exception.getMessage());
        verify(studentRepository, times(0)).deleteById(studentId);
    }

    @Test
    void findStudentById() {
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        Student foundStudent = studentService.findStudentById(studentId);
        assertNotNull(foundStudent);
        assertEquals(existingStudent.getName(), foundStudent.getName());
        assertEquals(existingStudent.getEmail(), foundStudent.getEmail());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void listAllStudents() {
        List<Student> students = Arrays.asList(existingStudent, createStudent("Maria Souza", "maria.souza@example.com"));
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> allStudents = studentService.findAllStudents();

        assertNotNull(allStudents);
        assertEquals(2, allStudents.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void listAllStudents_EmptyList() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList());
        List<Student> allStudents = studentService.findAllStudents();

        assertNotNull(allStudents);
        assertTrue(allStudents.isEmpty());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void findStudentById_NotFound() {
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.findStudentById(studentId);
        });

        assertEquals("Estudante não encontrado!", exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
    }
}
