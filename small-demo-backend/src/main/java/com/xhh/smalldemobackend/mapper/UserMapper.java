package com.xhh.smalldemobackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhh.smalldemocommon.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    void addUser(@Param("u") User user);

    void updateUserAgeAndNameAndEmail(@Param("id") Long id, @Param("age") Integer age, @Param("name") String name, @Param("email") String email);
}
