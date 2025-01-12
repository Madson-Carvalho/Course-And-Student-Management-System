package com.courseAndStudentManagementSystem.service;

import com.courseAndStudentManagementSystem.model.Course;
import com.courseAndStudentManagementSystem.model.Student;
import com.courseAndStudentManagementSystem.model.Teacher;
import com.courseAndStudentManagementSystem.repository.CourseRepository;
import com.courseAndStudentManagementSystem.repository.StudentRepository;
import com.courseAndStudentManagementSystem.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.courseAndStudentManagementSystem.utils.ValidateUtil.validateNotNullOrEmpty;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Course createCourse(Course course) {
        validateFields(course);

        Teacher teacher = course.getTeacher();
        if (teacher != null) {
            if (!teacherRepository.existsById(teacher.getId())) {
                throw new RuntimeException("Professor não encontrado!");
            }
            course.setTeacher(teacher);
        }

        Course savedCourse = courseRepository.save(course);

        for (Student student : course.getStudents()) {
            student.getCourses().add(savedCourse);
            studentRepository.save(student);
        }

        return savedCourse;
    }

    public Course updateCourse(UUID courseId, Course course) {
        validateFields(course);

        Course existingCourse = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Curso não encontrado!"));

        existingCourse.setName(course.getName());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setInitialDate(course.getInitialDate());
        existingCourse.setFinalDate(course.getFinalDate());

        Teacher teacher = course.getTeacher();
        if (teacher != null) {
            existingCourse.setTeacher(teacher);

            teacher.getCourses().add(existingCourse);
            teacherRepository.save(teacher);
        }

        existingCourse.getStudents().clear();
        for (Student student : course.getStudents()) {
            student.getCourses().add(existingCourse);
            studentRepository.save(student);
            existingCourse.getStudents().add(student);
        }

        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(UUID courseId) {
        findCourseById(courseId);
        courseRepository.deleteById(courseId);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(UUID courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Curso não encontrado!"));
    }

    private void validateFields(Course course){
        validateNotNullOrEmpty(course.getName(),"O nome do curso é obrigatório!");
    }}
