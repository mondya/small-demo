package com.xhh.smalldemobackend.service.student;

import com.xhh.smalldemocommon.pojo.Student;

import java.util.Map;

public interface StudentService {
    
    Map<String, Object> getAllStudentByLimit(String searchValue, int p, int s);
    
    void insertOneStudent(String code, String name);
    
    Student getOne(Long studentId);
    
    void updateStudent(Long studentId, String name, String code);
    
    void insertOne(Student student);
}
