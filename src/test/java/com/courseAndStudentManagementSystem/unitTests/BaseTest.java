package com.courseAndStudentManagementSystem.unitTests;

import com.courseAndStudentManagementSystem.model.Course;
import com.courseAndStudentManagementSystem.model.Student;
import com.courseAndStudentManagementSystem.model.Teacher;
import com.courseAndStudentManagementSystem.repository.CourseRepository;
import com.courseAndStudentManagementSystem.repository.StudentRepository;
import com.courseAndStudentManagementSystem.repository.TeacherRepository;
import org.mockito.Mock;

import java.time.Instant;

public abstract class BaseTest {

    @Mock
    protected CourseRepository courseRepository;

    @Mock
    protected TeacherRepository teacherRepository;

    @Mock
    protected StudentRepository studentRepository;

    protected Teacher   createTeacher(String name, String email) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setEmail(email);
        return teacher;
    }

    protected Course createCourse(String name, String description) {
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setInitialDate(Instant.parse("2024-10-28T00:00:00Z"));
        course.setFinalDate(Instant.parse("2024-10-29T00:00:00Z"));
        return course;
    }

    protected Student createStudent(String name, String email) {
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setDateOfBirth(Instant.now());
        return student;
    }
}
