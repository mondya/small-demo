package com.xhh.smalldemobackend.service.user;

import com.xhh.smalldemobackend.module.vo.UserVO;
import com.xhh.smalldemocommon.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String, Object> getAllUserPage(String searchValue, int p, int s);

    User getUserById(Long id);
    
    User fetchUserByMobile(String mobile);
    
    User getUserByMobileAndPWD(String mobile, String password);

    void updateByUserId(User user);

    void updateByUserIdV2(Long id, String name, Integer age, String email);

    void addUser(UserVO userVO);

    void deleteUserById(Long id);

    void saveUser(UserVO userVO);

    void deleteBatch(List<Long> ids);
}
