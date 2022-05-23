package com.xhh.smalldemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhh.smalldemo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    Integer addUser(@Param("u") User user);

    Integer updateUserAgeAndNameAndEmail(@Param("id") Long id, @Param("age") Integer age, @Param("name") String name, @Param("email") String email);
}
