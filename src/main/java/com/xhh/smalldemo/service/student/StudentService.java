package com.xhh.smalldemo.service.student;

import com.xhh.smalldemo.pojo.Student;

import java.util.List;

public interface StudentService {
    
    List<Student> getAllStudent();
    
    void insertOneStudent(String code, String name);
}
