package com.xhh.smalldemo.service.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhh.smalldemo.mapper.StudentMapper;
import com.xhh.smalldemo.pojo.Student;
import com.xhh.smalldemo.utils.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService{
    
    @Resource
    StudentMapper studentMapper;
    
    @Override
    public Map<String,Object> getAllStudentByLimit(String searchValue, int p, int s) {
        Map<String, Object> map = new HashMap<>();
        Page<Student> page = new Page<Student>(p,s);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
        queryWrapper.eq("status", (byte)1);
        if (StringUtils.isNotBlank(searchValue)){
            queryWrapper.and( i -> i.like("name", searchValue).or().like("code", searchValue));
        }
        IPage<Student> iPage = studentMapper.selectPage(page, queryWrapper);
        map.put("list", iPage.getRecords());
        map.put("total", iPage.getTotal());
        return map;
    }

    @Override
    public void insertOneStudent(String code, String name) {
        Student student = new Student.Builder().code(code).name(name).status((byte)1).build();
        
        this.saveStudent(student);
    }

    @Override
    public Student getOne(Long studentId) {
        return studentMapper.selectById(studentId);
    }

    @Override
    public void updateStudent(Long studentId, String name, String code) {
        Student student = studentMapper.selectById(studentId);
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("code", code);
        if (ObjectUtil.checkChange(student, map)){
            student.setName(name);
            student.setCode(code);
            studentMapper.updateById(student);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    void saveStudent(Student student){
        studentMapper.insert(student);
    }
}
