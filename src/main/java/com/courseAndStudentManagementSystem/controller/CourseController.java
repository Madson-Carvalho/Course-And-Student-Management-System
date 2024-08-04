package com.courseAndStudentManagementSystem.controller;

import com.courseAndStudentManagementSystem.model.Course;
import com.courseAndStudentManagementSystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/create-course")
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("/update-course/{id}")
    public Course updateCourse(@PathVariable UUID id, @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/delete-course/{id}")
    public void deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/find-all-courses")
    public List<Course> findAllCourses() {
        return courseService.findAllCourses();
    }

    @GetMapping("find-course-by-id/{id}")
    public Course findCourseById(@PathVariable UUID id) {
        return courseService.findCourseById(id);
    }
}
