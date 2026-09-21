package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "students")
@Schema(name = "Student", description = "Thông tin sinh viên")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Mã sinh viên, tự động tạo khi thêm mới", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    @Schema(description = "Họ và tên sinh viên", example = "Nguyen Van A", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "Tuổi sinh viên", example = "20", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer age;
    @Schema(description = "Địa chỉ email", example = "a@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @Column(name = "gender", columnDefinition = "NVARCHAR(20)")
    @Schema(description = "Giới tính", example = "Nam", requiredMode = Schema.RequiredMode.REQUIRED)
    private String gender;

    public Student() {
    }

    public Student(Integer id, String name, Integer age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    // Getter & Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}