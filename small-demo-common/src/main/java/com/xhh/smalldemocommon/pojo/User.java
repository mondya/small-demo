package com.xhh.smalldemocommon.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
@ApiModel(value = "user", description = "用户表")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String mobile;
    // 加密后的密码
    private String password;
    private String code;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime dateCreated;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastUpdated;
    private Byte status;
    
    public User(String name){
        this.name = name;
    }
}
