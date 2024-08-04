package com.courseAndStudentManagementSystem.service;

import com.courseAndStudentManagementSystem.model.Course;
import com.courseAndStudentManagementSystem.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(UUID courseId, Course course) {
        Course existingCourse = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Curso não encontrado!"));

        existingCourse.setName(course.getName());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setInitialDate(course.getInitialDate());
        existingCourse.setFinalDate(course.getFinalDate());
        existingCourse.setTeacher(course.getTeacher());

        existingCourse.getStudents().clear();
        existingCourse.getStudents().addAll(course.getStudents());

        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(UUID courseId) {
        courseRepository.deleteById(courseId);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(UUID courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }
}
