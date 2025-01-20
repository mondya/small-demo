package com.xhh.smalldemocommon.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GsonUser {
    private int id;
    private int age;
    private String name;
    private GsonAddress address;
    private List<String> hobby;
}
