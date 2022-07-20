package com.xhh.smalldemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhh.smalldemo.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    
}
