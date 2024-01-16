package com.xhh.smalldemobackend.controller;

import com.xhh.smalldemobackend.exception.SmallDemoException;
import com.xhh.smalldemobackend.module.vo.UserVO;
import com.xhh.smalldemocommon.pojo.User;
import com.xhh.smalldemobackend.service.user.UserService;
import com.xhh.smalldemobackend.module.vo.common.ResultVO;
import com.xhh.smalldemocommon.utils.ObjectUtil;
import com.xhh.smalldemocommon.utils.ToStringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/1.0/api/test")
@Api(value = "用户", tags = "user相关接口")
public class UserController {

    @Autowired
    UserService userService;


    @ApiOperation(value = "查询所有user")
    @RequestMapping(method = RequestMethod.GET)
    ResultVO getAllUser(@RequestParam(value = "p", required = false, defaultValue = "1") int p,
                        @RequestParam(value = "s", required = false, defaultValue = "30") int s,
                        @RequestParam(required = false) String searchValue) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(1);
        Map<String, Object> allUserPage = userService.getAllUserPage(searchValue, p, s);
        resultVO.setResult(allUserPage);
        return resultVO;
    }
    
    @ApiOperation(value = "更新用户")
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

            if (StringUtils.hasLength(name)) {
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
        } catch (Exception e) {
            resultVO = resultVO.failure();
            log.info("update failed id:{},exception:{}", id, e);
        }
        return resultVO;
    }

    @ApiOperation(value = "更新用户v2")
    @PutMapping("/updateV2")
    ResultVO updateV2(@RequestParam("id") Long id,
                      @RequestParam("name") String name,
                      @RequestParam("age") Integer age,
                      @RequestParam("email") String email) {
        ResultVO resultVO = new ResultVO();
        User user = userService.getUserById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);
        map.put("email", email);
        if (ObjectUtils.isEmpty(user)) {
            return resultVO.failure();
        } else {
            if (ObjectUtil.checkChange(user, map)) {
                return resultVO.success();
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
            resultVO.setStatus(1);
            userService.updateByUserIdV2(id, name, age, email);
        } catch (Exception e) {
            resultVO.setStatus(0);
            log.error("update failed,exception:{},id:{}", e, id);
        }

        return resultVO;
    }

    @ApiOperation(value = "保存用户")
    @PostMapping(value = "/save")
    ResultVO save(@RequestBody UserVO userVO) {
        ResultVO resultVO = new ResultVO();
        try {
            System.out.println("------->" + userVO.getUId());
//            resultVO.setStatus(1);
//            userService.addUser(userVO);
        } catch (Exception e) {
            resultVO = resultVO.failure();
            log.error("添加失败,userVO:{}", userVO);
        }
        return resultVO;
    }

    @PostMapping("/add")
    ResultVO add(UserVO userVO) {
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setStatus(1);
            userService.saveUser(userVO);
        } catch (Exception e){
            resultVO.setStatus(0);
            log.error("add user failed, exception:{}, user:{}", e, userVO);
        }
        return resultVO;
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/delete/{id}")
    ResultVO delete(@PathVariable("id") Long id) {
        ResultVO resultVO = new ResultVO();
        try {
            userService.deleteUserById(id);
            resultVO = resultVO.success();
        } catch (Exception e) {
            log.error("删除人员失败,id:{},message:", id, e);
            throw new SmallDemoException(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("/delete")
    ResultVO batchDelete(@RequestParam("ids") String ids) {
        ResultVO resultVO = new ResultVO();
        try {
            List<Long> idList = new ArrayList<Long>();
            if (StringUtils.hasLength(ids)) {
                idList = ToStringUtils.stringIdsToListLong(ids);
                userService.deleteBatch(idList);
                resultVO = resultVO.success();
            }
        } catch (Exception e) {
            resultVO = resultVO.failure();
            log.error("删除失败,ids:{},exception:{}", ids, e);
        }
        return resultVO;
    }


}
