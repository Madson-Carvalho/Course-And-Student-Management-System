package com.courseAndStudentManagementSystem.service;

import com.courseAndStudentManagementSystem.model.Course;
import com.courseAndStudentManagementSystem.model.Student;
import com.courseAndStudentManagementSystem.repository.CourseRepository;
import com.courseAndStudentManagementSystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class StudentService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Student createStudent(Student student) {

        if (!EMAIL_PATTERN.matcher(student.getEmail()).matches()) {
            throw new IllegalArgumentException("Email inválido!");
        }

        Student savedStudent = studentRepository.save(student);

        for (Course course : student.getCourses()) {
            course.getStudents().add(savedStudent);
            courseRepository.save(course);
        }

        return savedStudent;
    }

    public Student updateStudent(UUID studentId, Student student) {

        if (!EMAIL_PATTERN.matcher(student.getEmail()).matches()) {
            throw new IllegalArgumentException("Email inválido!");
        }

        Student existingStudent = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Estudante não encontrado!"));

        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setDateOfBirth(student.getDateOfBirth());

        existingStudent.getCourses().clear();
        for (Course course : student.getCourses()) {
            course.getStudents().add(existingStudent);
            courseRepository.save(course);
            existingStudent.getCourses().add(course);
        }

        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(UUID studentId) {
        studentRepository.deleteById(studentId);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Student findStudentById(UUID studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Estudante não encontrado!"));
    }
}
