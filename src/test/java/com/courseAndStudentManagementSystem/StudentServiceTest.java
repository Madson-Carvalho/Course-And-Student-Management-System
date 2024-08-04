package com.courseAndStudentManagementSystem;

import com.courseAndStudentManagementSystem.model.Student;
import com.courseAndStudentManagementSystem.repository.StudentRepository;
import com.courseAndStudentManagementSystem.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStudent() {
        Student student = new Student();
        student.setName("Reginaldo Rossi");
        student.setEmail("reginaldo_rossi@example.com");
        student.setDateOfBirth(Instant.now());

        when(studentRepository.save(student)).thenReturn(student);

        Student createdStudent = studentService.createStudent(student);

        assertNotNull(createdStudent);
        assertEquals("Reginaldo Rossi", createdStudent.getName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void updateStudent() {
        UUID studentId = UUID.randomUUID();
        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setName("Reginaldo Rossi");
        existingStudent.setEmail("reginaldo_rossi@example.com");

        Student updatedStudent = new Student();
        updatedStudent.setName("Reginaldo Rossi atualizado");
        updatedStudent.setEmail("reginaldo.rossi@example.com");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);

        Student result = studentService.updateStudent(studentId, updatedStudent);

        assertNotNull(result);
        assertEquals("Reginaldo Rossi atualizado", result.getName());
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    void deleteStudent() {
        UUID studentId = UUID.randomUUID();
        doNothing().when(studentRepository).deleteById(studentId);

        studentService.deleteStudent(studentId);

        verify(studentRepository, times(1)).deleteById(studentId);
    }
}
