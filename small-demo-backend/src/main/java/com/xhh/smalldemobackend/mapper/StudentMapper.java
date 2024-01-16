package com.xhh.smalldemobackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhh.smalldemocommon.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    
}
