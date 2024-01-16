package com.xhh.smalldemobackend.controller;

import com.xhh.smalldemocommon.pojo.User;
import com.xhh.smalldemobackend.service.user.UserService;
import com.xhh.smalldemobackend.module.vo.common.ResultVO;
import com.xhh.smalldemocommon.utils.JwtTokenUtil;
import com.xhh.smalldemocommon.utils.PasswordUtil;
import io.swagger.annotations.Api;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/1.0/api")
@Api(value = "登入接口", tags = "登入")
public class LoginController {

    @Resource
    UserService userService;

    @RequestMapping("/login")
    public ResultVO login(@RequestParam("mobile") String mobile,
                          @RequestParam(value = "password", required = false) String password,
                          @RequestParam(value = "checkNo", required = false) String checkNo) {
        Assert.hasLength(mobile, "手机号不能为空");

        ResultVO resultVO = new ResultVO();
        User user = userService.fetchUserByMobile(mobile);
        
        if (ObjectUtils.isEmpty(user)){
            throw new RuntimeException("用户不存在");
        }
        
        if(StringUtils.hasLength(password)){
            if (PasswordUtil.validatePassword(password, user.getPassword())){
                Map<String, Object> claims = new HashMap<>();
                claims.put("userId", user.getId());
                claims.put("mobile", mobile);
                claims.put("userName", user.getName());
                String token = JwtTokenUtil.generateToken(claims);
                
                resultVO.getResult().put("userId", user.getId());
                resultVO.getResult().put("mobile", mobile);
                resultVO.getResult().put("userName", user.getName());
                resultVO.getResult().put("token", token);
            }
        }

        return resultVO;
    }
}
