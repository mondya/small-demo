package com.xhh.smalldemo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "教师表")
@TableName(value = "teacher")
public class Teacher {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String code;
    private String mobile;
}
