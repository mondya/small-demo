package com.xhh.smalldemo.controller;

import com.xhh.smalldemo.utils.ToStringUtils;
import com.xhh.smalldemo.vo.UserVO;
import com.xhh.smalldemo.pojo.User;
import com.xhh.smalldemo.service.UserService;
import com.xhh.smalldemo.utils.ObjectUtil;
import com.xhh.smalldemo.vo.common.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/1.0/api")
public class TestController {

    @Autowired
    UserService userService;

    @RequestMapping("/hello")
    public String hello() {
        return "helloWorld你好";
    }


    @RequestMapping("/users")
    ResultVO getAllUser(@RequestParam(value = "p", required = false, defaultValue = "1") int p,
                        @RequestParam(value = "s", required = false, defaultValue = "30") int s) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(1);
        Map<String, Object> allUserPage = userService.getAllUserPage(p, s);
        resultVO.setResult(allUserPage);
        return resultVO;
    }

    @PutMapping("/update")
    ResultVO update(@RequestParam("id") Long id,
                  @RequestParam("name") String name,
                  @RequestParam("age") Integer age,
                  @RequestParam("email") String email) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(1);
        User user = userService.getUserById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);
        map.put("email", email);
        if (ObjectUtils.isEmpty(user)) {
            return resultVO.failure();
        } else {
            if (ObjectUtil.checkChange(user, map)) {
                resultVO.setMessage("不做更改，success");
                return resultVO;
            }

            if (name != null && !name.equals("")) {
                user.setName(name);
            }

            if (age != null) {
                user.setAge(age);
            }

            if (email != null && !email.equals("")) {
                user.setEmail(email);
            }
            user.setLastUpdated(LocalDateTime.now());

        }
        try {
            userService.updateByUserId(user);
        } catch (Exception e){
            resultVO = resultVO.failure();
            log.info("更新失败 id:{}",id);
        }
        return resultVO;
    }

    @PutMapping("/updateV2")
    String updateV2(@RequestParam("id") Long id,
                    @RequestParam("name") String name,
                    @RequestParam("age") Integer age,
                    @RequestParam("email") String email) {
        User user = userService.getUserById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);
        map.put("email", email);
        if (ObjectUtils.isEmpty(user)) {
            return "error";
        } else {
            if (ObjectUtil.checkChange(user, map)) {
                return "不做更改success";
            }

            if (name != null && !name.equals("")) {
                user.setName(name);
            }

            if (age != null) {
                user.setAge(age);
            }

            if (email != null && !email.equals("")) {
                user.setEmail(email);
            }
            user.setLastUpdated(LocalDateTime.now());

        }
        return userService.updateByUserIdV2(id, name, age, email);
    }

    @PostMapping(value = "/save")
    ResultVO save(@RequestBody UserVO userVO) {
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setStatus(1);
            userService.addUser(userVO);
        } catch (Exception e){
            resultVO = resultVO.failure();
            log.error("添加失败,userVO:{}",userVO);
        }
        return resultVO;
    }

    @PostMapping("/add")
    String add(UserVO userVO) {
        return userService.saveUser(userVO);
    }

    @DeleteMapping("/delete/{id}")
    ResultVO delete(@PathVariable("id") Long id) {
        ResultVO resultVO = new ResultVO();
        try {
            userService.deleteUserById(id);
            resultVO = resultVO.success();
        } catch (Exception e){
            resultVO = resultVO.failure();
            log.error("删除人员失败,id{},message:{}",id,e);
        }
        return resultVO;
    }

    @DeleteMapping("/delete")
    String batchDelete(@RequestParam("ids") String ids) {
        String s = "";
        List<Long> idList = new ArrayList<Long>();
        if (StringUtils.hasLength(ids)) {
            idList = ToStringUtils.stringIdsToListLong(ids);
            s = userService.deleteBatch(idList);
        }
        return s;
    }


}
