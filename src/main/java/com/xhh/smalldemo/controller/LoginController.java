package com.xhh.smalldemo.controller;

import com.xhh.smalldemo.pojo.User;
import com.xhh.smalldemo.service.user.UserService;
import com.xhh.smalldemo.vo.common.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
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
                          @RequestParam(value = "password", required = false) String password,
                          @RequestParam(value = "checkNo", required = false) String checkNo) {
        Assert.hasLength(mobile, "手机号不能为空");
        if (StringUtils.hasLength(checkNo)){
            // todo 查询验证码相关操作,和redis缓存中的code做对比
        }
        if(StringUtils.hasLength(password)){
            // todo 加密密码和数据库中加密后的密码作对比
        }
        ResultVO resultVO = new ResultVO();
        User user = userService.getUserByMobileAndPWD(mobile, password);
        if (!ObjectUtils.isEmpty(user)) {
            resultVO = resultVO.success();
        } else {
            resultVO.setStatus(0);
            resultVO.setMessage("登入失败，用户为空");
        }

        return resultVO;
    }
}
