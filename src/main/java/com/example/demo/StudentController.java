package com.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public List<Student> listStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/api/students")
    public List<Student> getStudents() {
        List<Student> list = new java.util.ArrayList<>();
        list.add(new Student(1, "A", 20));
        list.add(new Student(2, "B", 21));
        return list;
    }

    @GetMapping("/api/student")
    public Student getStudent() {
        return new Student(1, "Nguyen Van A", 20);
    }

    @GetMapping({"/students/{id}", "/api/student/{id}"})
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}