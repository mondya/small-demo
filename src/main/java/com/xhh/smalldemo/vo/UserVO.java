package com.xhh.smalldemo.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {
    private String name;
    private Integer age;
    private String email;
    
}
