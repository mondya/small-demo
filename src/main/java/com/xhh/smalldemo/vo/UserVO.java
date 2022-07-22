package com.xhh.smalldemo.vo;


import com.google.common.base.Converter;
import com.xhh.smalldemo.pojo.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {
    private String name;
    private Integer age;
    private String email;
    
    private UserVO buildVO(User user){
        return new BuildVO().convert(user);
    }
    
    
    private class BuildVO extends Converter<User, UserVO> {

        @Override
        protected UserVO doForward(User user) {
            UserVO userVO = new UserVO();
            userVO.setAge(user.getAge());
            return userVO;
        }

        @Override
        protected User doBackward(UserVO userVO) {
            return null;
        }
    }
}
