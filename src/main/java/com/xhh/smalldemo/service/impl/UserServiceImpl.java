package com.xhh.smalldemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhh.smalldemo.vo.UserVO;
import com.xhh.smalldemo.mapper.UserMapper;
import com.xhh.smalldemo.pojo.User;
import com.xhh.smalldemo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public List<User> getAllUser(int p, int s) {
        Page<User> userPage = new Page<User>(p, s);
        List<User> users = new ArrayList<>();
        IPage<User> userIPageList = userMapper.selectPage(userPage, new QueryWrapper<User>().eq("status", (byte) 1));
        users = userIPageList.getRecords();
        return users;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public String updateByUserId(User user) {
        try {
            userMapper.updateById(user);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updateByUserIdV2(Long id, String name, Integer age, String email) {
        try {
            userMapper.updateUserAgeAndNameAndEmail(id, age, name, email);
            return "success";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String addUser(UserVO userVO) {
        try {
            User user = new User();
            BeanUtils.copyProperties(userVO, user);
            user.setDateCreated(LocalDateTime.now());
            user.setLastUpdated(LocalDateTime.now());
            user.setStatus((byte) 1);
            userMapper.insert(user);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteUserById(Long id) {
        try {
            User user = userMapper.selectById(id);
            user.setStatus((byte) 0);
            user.setLastUpdated(LocalDateTime.now());
            userMapper.updateById(user);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String saveUser(UserVO userVO) {
        try {
            User user = new User();
            BeanUtils.copyProperties(userVO, user);
            user.setDateCreated(LocalDateTime.now());
            user.setLastUpdated(LocalDateTime.now()); 
            user.setStatus((byte) 1);
            userMapper.addUser(user);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteBatch(List<Long> ids) {
        try {
            List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("status", (byte) 1).in("id", ids));
            if (CollectionUtils.isEmpty(users)) {
                return "success";
            }
            if (!CollectionUtils.isEmpty(users)) {
                users.forEach((User u) -> {
                    u.setStatus((byte) 0);
                    u.setLastUpdated(LocalDateTime.now());
                });
            }
            super.saveOrUpdateBatch(users);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
