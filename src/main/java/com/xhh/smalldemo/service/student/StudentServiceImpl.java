package com.xhh.smalldemo.service.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhh.smalldemo.mapper.StudentMapper;
import com.xhh.smalldemo.pojo.Student;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService{
    
    @Resource
    StudentMapper studentMapper;
    
    @Override
    public Map<String,Object> getAllStudentByLimit(int p, int s) {
        Map<String, Object> map = new HashMap<>();
        Page<Student> page = new Page<Student>(p,s);
        IPage<Student> iPage = studentMapper.selectPage(page, new QueryWrapper<Student>().eq("status", (byte)1));
        map.put("list", iPage.getRecords());
        map.put("total", iPage.getTotal());
        return map;
    }

    @Override
    public void insertOneStudent(String code, String name) {
        Student student = new Student.Builder().code(code).name(name).dateCreated(LocalDateTime.now()).
                lastUpdated(LocalDateTime.now()).status((byte)1).build();
        
        studentMapper.insert(student);
    }
}
