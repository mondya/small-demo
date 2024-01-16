package com.xhh.smalldemobackend.module.vo;


import com.google.common.base.Converter;
import com.xhh.smalldemocommon.pojo.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {
    private String name;
    private Integer age;
    private String email;
    // 注意：这种参数在lombok中默认生成的set方法为setUId()，而java默认规则生成set方法为setuId，此时如果没有重写set方法会导致从前端获取的参数为null值
    // 正确的做法是从第三个字母开始才考虑大写
    private Long uId;


    public void setuId(Long uId) {
        this.uId = uId;
    }
    
    
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
