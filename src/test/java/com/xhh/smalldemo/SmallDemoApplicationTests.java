package com.xhh.smalldemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xhh.smalldemo.mapper.UserMapper;
import com.xhh.smalldemo.pojo.User;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SmallDemoApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void findAll() {
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            System.out.println(user.toString());
        }
    }
    
    @Test
    void findById(){
        User user = userMapper.selectById(1);
        User user2 = userMapper.selectOne(new QueryWrapper<User>().eq("id",2L));
        System.out.println(user2.toString());
    }
}
