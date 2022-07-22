package com.xhh.smalldemo.service.student;

import com.xhh.smalldemo.pojo.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {
    
    Map<String, Object> getAllStudentByLimit(int p, int s);
    
    void insertOneStudent(String code, String name);
}
