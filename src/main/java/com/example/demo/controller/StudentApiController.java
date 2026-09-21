package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.demo.Student;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student API", description = "Quản lý sinh viên")
public class StudentApiController {

    private final StudentService studentService;

    public StudentApiController(StudentService studentService) {
        this.studentService = studentService;
    }

        @Operation(summary = "Thêm sinh viên", description = "ID được tự động tạo, không cần gửi id trong request.")
        @ApiResponse(responseCode = "201", description = "Tạo sinh viên thành công")
    @PostMapping
        public ResponseEntity<Student> createStudent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(schema = @Schema(implementation = Student.class), examples = @ExampleObject(
                    value = "{\"name\":\"Nguyen Van A\",\"age\":20,\"gender\":\"Nam\",\"email\":\"a@example.com\"}")))
            @RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.saveStudent(student));
    }

        @Operation(summary = "Xóa sinh viên (REST chuẩn)")
        @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Xóa thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sinh viên")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteStudentRest(
            @Parameter(description = "ID sinh viên", example = "1") @PathVariable int id) {
        return deleteStudent(id);
        }

        @Operation(summary = "Cập nhật sinh viên (REST chuẩn)")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sinh viên")
        })
        @PutMapping("/{id}")
        public ResponseEntity<Student> updateStudentRest(
            @Parameter(description = "ID sinh viên", example = "1") @PathVariable int id,
            @RequestBody Student student) {
        return updateStudent(id, student);
        }

        @Operation(summary = "Xóa sinh viên (tương thích endpoint cũ)")
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        if (studentService.getStudentById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Tìm kiếm sinh viên", description = "Dùng name hoặc keyword để tìm theo tên.")
    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String keyword) {
        return studentService.searchStudents(name != null ? name : keyword);
    }

    @Operation(summary = "Lấy sinh viên theo ID")
    @ApiResponse(responseCode = "404", description = "Không tìm thấy sinh viên")
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lấy tất cả sinh viên")
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @Operation(summary = "Cập nhật sinh viên (tương thích endpoint cũ)")
    @PostMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        if (studentService.getStudentById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        student.setId(id);
        return ResponseEntity.ok(studentService.saveStudent(student));
    }
}