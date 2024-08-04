package com.courseAndStudentManagementSystem.controller;

import com.courseAndStudentManagementSystem.model.Student;
import com.courseAndStudentManagementSystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/create-student")
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/update-student/{id}")
    public Student updateStudent(@PathVariable UUID id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/delete-student/{id}")
    public void deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/find-all-students")
    public List<Student> findAllStudents() {
        return studentService.findAllStudents();
    }

    @GetMapping("find-students-by-id/{id}")
    public Student findStudentById(@PathVariable UUID id) {
        return studentService.findStudentById(id);
    }
}
