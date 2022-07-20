package com.xhh.smalldemo.controller;

import com.xhh.smalldemo.service.student.StudentService;
import com.xhh.smalldemo.vo.common.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/student")
@ResponseBody
@Slf4j
public class StudentController {
    
    @Resource
    StudentService studentService;
    
    @PostMapping()
    public ResultVO addStudent(@RequestParam("code") String code,
                               @RequestParam("name") String name){
        ResultVO resultVO = new ResultVO();
        try {
            studentService.insertOneStudent(code, name);
            resultVO = resultVO.success();
        } catch (Exception e){
            log.error("保存失败",e);
            resultVO = resultVO.failure();
        }
        
        return resultVO;
    }
}
