package com.xhh.smalldemo.controller;

import com.xhh.smalldemo.utils.ToStringUtils;
import com.xhh.smalldemo.vo.UserVO;
import com.xhh.smalldemo.pojo.User;
import com.xhh.smalldemo.service.UserService;
import com.xhh.smalldemo.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class TestController {

    @Autowired
    UserService userService;

    @RequestMapping("/hello")
    public String hello() {
        return "helloWorld你好";
    }


    @RequestMapping("/users")
    List<User> getAllUser(@RequestParam(value = "p", required = false, defaultValue = "1") int p,
                          @RequestParam(value = "s", required = false, defaultValue = "30") int s) {
        return userService.getAllUser(p, s);
    }

    @PutMapping("/update")
    String update(@RequestParam("id") Long id,
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
        return userService.updateByUserId(user);
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
    String save(@RequestBody UserVO userVO) {
        return userService.addUser(userVO);
    }

    @PostMapping("/add")
    String add(UserVO userVO) {
        return userService.saveUser(userVO);
    }

    @DeleteMapping("/delete/{id}")
    String delete(@PathVariable("id") Long id) {
        return userService.deleteUserById(id);
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
