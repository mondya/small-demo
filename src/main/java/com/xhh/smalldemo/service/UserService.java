package com.xhh.smalldemo.service;

import com.xhh.smalldemo.vo.UserVO;
import com.xhh.smalldemo.pojo.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser(int p, int s);

    User getUserById(Long id);

    String updateByUserId(User user);
    
    String updateByUserIdV2(Long id,String name,Integer age,String email);

    String addUser(UserVO userVO);

    String deleteUserById(Long id);

    String saveUser(UserVO userVO);
    
    String deleteBatch(List<Long> ids);
}
