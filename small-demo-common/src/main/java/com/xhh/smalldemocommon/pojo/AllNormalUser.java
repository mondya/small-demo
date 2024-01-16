package com.xhh.smalldemocommon.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("all_normal_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "user表视图")
public class AllNormalUser implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String mobile;
    private String password;
    private String code;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
    private Byte status;
}
