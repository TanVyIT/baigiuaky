package com.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/api/students")
    @ResponseBody
    public List<Student> getStudents() {
        List<Student> list = new java.util.ArrayList<>();
        list.add(new Student(1, "Nguyễn Văn A", 20, "Nam"));
        list.add(new Student(2, "Trần Thị B", 21, "Nữ"));
        return list;
    }

    @GetMapping("/api/student")
    @ResponseBody
    public Student getStudent() {
        return new Student(1, "Nguyễn Văn A", 20, "Nam");
    }

    @GetMapping({"/students/{id}", "/api/student/{id}"})
    @ResponseBody
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}