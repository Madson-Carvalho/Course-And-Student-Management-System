package com.courseAndStudentManagementSystem.service;

import com.courseAndStudentManagementSystem.model.Student;
import com.courseAndStudentManagementSystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(UUID studentId, Student student) {
        Student existingStudent = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Estudante não encontrado!"));

        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setDateOfBirth(student.getDateOfBirth());

        existingStudent.getCourses().clear();
        existingStudent.getCourses().addAll(student.getCourses());

        return studentRepository.save(student);
    }

    public void deleteStudent(UUID studentId) {
        studentRepository.deleteById(studentId);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Student findStudentById(UUID studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }
}
