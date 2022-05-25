package com.xhh.smalldemo.controller;

import com.xhh.smalldemo.pojo.User;
import com.xhh.smalldemo.service.user.UserService;
import com.xhh.smalldemo.vo.common.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1.0/api")
public class LoginController {
    
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public ResultVO login(@RequestParam("mobile") String mobile,
                          @RequestParam("password") String password) {
        Assert.hasLength(mobile,"手机号不能为空");
        Assert.hasLength(password,"密码不能为空");
        ResultVO resultVO = new ResultVO();
        User user = userService.getUserByMobileAndPWD(mobile, password);
        if (!ObjectUtils.isEmpty(user)){
            resultVO =  resultVO.success();
        } else {
            resultVO.setStatus(0);
            resultVO.setMessage("登入失败，用户为空");
        }
        
        return resultVO;
    }
}
