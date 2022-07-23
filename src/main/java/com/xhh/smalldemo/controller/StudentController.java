package com.xhh.smalldemo.controller;

import com.xhh.smalldemo.pojo.Student;
import com.xhh.smalldemo.service.student.StudentService;
import com.xhh.smalldemo.vo.common.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/student")
@ResponseBody
@Slf4j
public class StudentController {
    
    @Resource
    StudentService studentService;
    
    @GetMapping("/student")
    public ResultVO index(@RequestParam(value = "p", defaultValue = "1") int p,
                          @RequestParam(value = "s", defaultValue = "30") int s,
                          @RequestParam(value = "searchValue", required = false) String searchValue){
        ResultVO resultVO = new ResultVO();
        try {
            Map<String, Object> allStudentByLimit = studentService.getAllStudentByLimit(searchValue,p, s);
            resultVO.getResult().put("list", allStudentByLimit.get("list"));
            resultVO.getResult().put("total", allStudentByLimit.get("total"));
        } catch (Exception e){
            resultVO = resultVO.failure();
            log.error("查询出错:", e);
        }
        return resultVO;
    }
    
    @GetMapping("/{id}")
    public ResultVO show(@PathVariable("id") Long id){
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(1);
        
        try {
            Student student = studentService.getOne(id);
            resultVO.getResult().put("student", student);
        } catch (Exception e){
            resultVO.setStatus(0);
            log.error("查询出错",e);
        }
        
        return resultVO;
    }
    
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
