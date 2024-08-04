package com.courseAndStudentManagementSystem.repository;

import com.courseAndStudentManagementSystem.model.Course;
import com.courseAndStudentManagementSystem.model.Student;

import java.util.UUID;

public interface ManagerCourse {
    public Student associateCourse(Course course, UUID id);
    public Student disassociateCourse(Course course, UUID id);
}
