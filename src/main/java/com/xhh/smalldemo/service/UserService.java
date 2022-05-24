package com.xhh.smalldemo.service;

import com.xhh.smalldemo.vo.UserVO;
import com.xhh.smalldemo.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String, Object> getAllUserPage(int p, int s);

    User getUserById(Long id);
    
    User getUserByMobileAndPWD(String mobile, String password);

    void updateByUserId(User user);

    void updateByUserIdV2(Long id, String name, Integer age, String email);

    void addUser(UserVO userVO);

    void deleteUserById(Long id);

    String saveUser(UserVO userVO);

    void deleteBatch(List<Long> ids);
}
