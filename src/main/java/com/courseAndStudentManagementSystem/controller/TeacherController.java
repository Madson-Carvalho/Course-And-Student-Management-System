package com.courseAndStudentManagementSystem.controller;

import com.courseAndStudentManagementSystem.model.Teacher;
import com.courseAndStudentManagementSystem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/create-teacher")
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }

    @PutMapping("/update-teacher/{id}")
    public Teacher updateTeacher(@PathVariable UUID id, @RequestBody Teacher teacher) {
        return teacherService.updateTeacher(id, teacher);
    }

    @DeleteMapping("/delete-teacher/{id}")
    public void deleteTeacher(@PathVariable UUID id) {
        teacherService.deleteTeacher(id);
    }

    @GetMapping("/find-all-teachers")
    public List<Teacher> findAllTeachers() {
        return teacherService.findAllTeacher();
    }

    @GetMapping("/find-teacher-by-id/{id}")
    public Teacher findTeacherById(@PathVariable UUID id) {
        return teacherService.findTeacherById(id);
    }
}
