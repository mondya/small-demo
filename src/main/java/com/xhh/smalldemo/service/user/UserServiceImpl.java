package com.xhh.smalldemo.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhh.smalldemo.vo.UserVO;
import com.xhh.smalldemo.mapper.UserMapper;
import com.xhh.smalldemo.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public Map<String, Object> getAllUserPage(String searchValue, int p, int s) {
        Map<String, Object> map = new HashMap<>();
        Page<User> page = new Page<User>(p, s);
        IPage<User> pageList = userMapper.selectPage(page, new QueryWrapper<User>().eq("status", (byte) 1).like("name","%" + searchValue + "%"));
        List<User> records = pageList.getRecords();
        long total = pageList.getTotal();
        map.put("list", records);
        map.put("total", total);
        return map;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getUserByMobileAndPWD(String mobile, String password) {
        return  userMapper.selectOne(new QueryWrapper<User>().eq("status", (byte) 1).eq("mobile", mobile).eq("password", password));
    }

    @Override
    public void updateByUserId(User user) {
        try {
            userMapper.updateById(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateByUserIdV2(Long id, String name, Integer age, String email) {
        try {
            userMapper.updateUserAgeAndNameAndEmail(id, age, name, email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addUser(UserVO userVO) {
        try {
            User user = new User();
            BeanUtils.copyProperties(userVO, user);
            user.setDateCreated(LocalDateTime.now());
            user.setLastUpdated(LocalDateTime.now());
            user.setStatus((byte) 1);
            userMapper.insert(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            User user = userMapper.selectById(id);
            user.setStatus((byte) 0);
            user.setLastUpdated(LocalDateTime.now());
            userMapper.updateById(user);
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
    public void deleteBatch(List<Long> ids) {
        try {
            List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("status", (byte) 1).in("id", ids));
            if (CollectionUtils.isEmpty(users)) {
                return;
            }
            if (!CollectionUtils.isEmpty(users)) {
                users.forEach((User u) -> {
                    u.setStatus((byte) 0);
                    u.setLastUpdated(LocalDateTime.now());
                });
            }
            super.saveOrUpdateBatch(users);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
