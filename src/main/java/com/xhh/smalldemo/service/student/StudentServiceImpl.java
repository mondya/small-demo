package com.xhh.smalldemo.service.student;

import com.xhh.smalldemo.mapper.StudentMapper;
import com.xhh.smalldemo.pojo.Student;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    
    @Resource
    StudentMapper studentMapper;
    
    @Override
    public List<Student> getAllStudent() {
        return null;
    }

    @Override
    public void insertOneStudent(String code, String name) {
        Student student = new Student.Builder().code(code).name(name).dateCreated(LocalDateTime.now()).
                lastUpdated(LocalDateTime.now()).status((byte)1).build();
        
        studentMapper.insert(student);
    }
}
