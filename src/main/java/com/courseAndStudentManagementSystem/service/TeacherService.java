package com.courseAndStudentManagementSystem.service;

import com.courseAndStudentManagementSystem.model.Course;
import com.courseAndStudentManagementSystem.model.Teacher;
import com.courseAndStudentManagementSystem.repository.CourseRepository;
import com.courseAndStudentManagementSystem.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Teacher createTeacher(Teacher teacher){
        Teacher savedTeacher = teacherRepository.save(teacher);

        for (Course course : teacher.getCourses()) {
            course.setTeacher(savedTeacher);
            courseRepository.save(course);
        }

        return savedTeacher;
    }

    public Teacher updateTeacher(UUID teacherId, Teacher teacher){
        Teacher existingTeacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Professor não encontrado!"));

        existingTeacher.setName(teacher.getName());
        existingTeacher.setEmail(teacher.getEmail());

        existingTeacher.getCourses().clear();
        for (Course course : teacher.getCourses()) {
            course.setTeacher(existingTeacher);
            courseRepository.save(course);
            existingTeacher.getCourses().add(course);
        }

        return teacherRepository.save(existingTeacher);
    }

    public void deleteTeacher(UUID teacherId){
        teacherRepository.deleteById(teacherId);
    }

    public List<Teacher> findAllTeacher(){
        return teacherRepository.findAll();
    }

    public Teacher findTeacherById(UUID teacherId){
        return teacherRepository.findById(teacherId).orElse(null);
    }
}
