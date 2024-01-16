package com.xhh.smalldemobackend.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhh.smalldemobackend.module.vo.UserVO;
import com.xhh.smalldemobackend.mapper.UserMapper;
import com.xhh.smalldemocommon.pojo.Student;
import com.xhh.smalldemocommon.pojo.User;
import com.xhh.smalldemobackend.service.student.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Resource
    StudentService studentService;


    @Override
    public Map<String, Object> getAllUserPage(String searchValue, int p, int s) {
        Map<String, Object> map = new HashMap<>();
        Page<User> page = new Page<User>(p, s);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", (byte) 1);
        if (StringUtils.isNotBlank(searchValue)) {
            queryWrapper.like("name", "\\" + searchValue);
        }
        queryWrapper.orderByDesc("id");
        IPage<User> pageList = userMapper.selectPage(page, queryWrapper);
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
    public User fetchUserByMobile(String mobile) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("mobile", mobile).eq("status", (byte) 1));
    }

    @Override
    public User getUserByMobileAndPWD(String mobile, String password) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("status", (byte) 1).eq("mobile", mobile).eq("password", password));
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
    @Transactional
    public void addUser(UserVO userVO) {
        try {
            User user = new User();
            BeanUtils.copyProperties(userVO, user);
            user.setDateCreated(LocalDateTime.now());
            user.setLastUpdated(LocalDateTime.now());
            user.setStatus((byte) 1);
            userMapper.insert(user);
            throw new RuntimeException("回滚");
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

    /**
     * 在不同类中, a方法调用b方法
     * a方法不加@Transactional注解，b不加@Transactional注解，则没有事务(即a,b方法插入的数据报错后不会回滚)
     * a方法不加@Transactional注解, b加@Transactional注解, b方法报错，b事务生效（数据插入失败），如果在b方法之外报错，则事务仍然失效，即b方法数据插入成功
     * a方法加@Transactional注解，不管b加不加@Transactional注解，事务都生效。（注意：如果b方法没有@Transactional注解,b方法被try-catch后，b方法报错，ab方法数据都不回滚；b方法有@Transactional注解,b方法即使被try-catch，ab方法的数据也会发生回滚，此时会抛出嵌套事务报错提示，但事务生效）
     *  exception:org.springframework.transaction.UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only
     * <p>
     * 在同一个类中，a方法调用b方法
     * a方法加@Transactional注解，不管b加不加@Transactional注解，不论是a方法报错还是b方法报错，事务都生效，数据回滚;注意：如果b方法被try-catch，则不论b方法是否有@Transactional注解，报错后数据都不回滚
     * a方法不加@Transactional注解，不管b加不加@Transactional注解，事务都无效，
     * <p>
     * <p>
     * 原因：AOP思想，a方法有@Transactional注解，spring在管理的时候，真正调用a方法时，实际执行的是代理类里面的方法，该代理类的方法已经包括了b方法的调用，已经成为一个方法，所以事务生效
     *
     * 总结：a方法有@Transactional注解，一般情况下ab方法在同一个事务中，事务都生效；另外一种情况：ab在不同类时,b方法try-catch且没有@Transactional注解，b方法报错，事务失效；b方法try-catch但是有@Transactional注解，事务仍然生效；ab相同类时，b被try-catch事务不生效。
     * a方法没有@Transactional注解,b有@Transactional注解，ab在同一个类中，不论b方法有没有被try-catch,b事务都失效；ab不同类，b不论是否被try-catch,b事务都生效；
     * a没有@Transactional注解，b也没有@Transactional注解，则没有事务
     *
     * @param userVO
     */

    @Override
    @Transactional
    public void saveUser(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        user.setDateCreated(LocalDateTime.now());
        user.setLastUpdated(LocalDateTime.now());
        user.setStatus((byte) 1);
        userMapper.insert(user);
        Student xhh = new Student.Builder().code("11111").name("xhh").build();
        try {
            studentService.insertOne(xhh);
        } catch (Exception e) {
            log.info("do nothing");
        }
        
//        try {
//            addUser(userVO);
//        } catch (Exception e) {
//            log.info("do nothing");
//        }

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
