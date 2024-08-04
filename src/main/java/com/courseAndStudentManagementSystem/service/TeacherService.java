package com.courseAndStudentManagementSystem.service;

import com.courseAndStudentManagementSystem.model.Teacher;
import com.courseAndStudentManagementSystem.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher createTeacher(Teacher teacher){
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(UUID teacherId, Teacher teacher){
        Teacher existingTeacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Professor não encontrado!"));

        existingTeacher.setName(teacher.getName());
        existingTeacher.setEmail(teacher.getEmail());

        existingTeacher.getCourses().clear();
        existingTeacher.getCourses().addAll(teacher.getCourses());

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
