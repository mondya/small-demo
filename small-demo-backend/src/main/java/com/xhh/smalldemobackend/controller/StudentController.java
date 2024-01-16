package com.xhh.smalldemobackend.controller;

import com.xhh.smalldemocommon.pojo.Student;
import com.xhh.smalldemobackend.service.student.StudentService;
import com.xhh.smalldemobackend.service.token.TokenService;
import com.xhh.smalldemobackend.module.vo.common.ResultVO;
import com.xhh.smalldemocommon.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
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
    
    @Resource
    TokenService tokenService;
    
    @GetMapping()
    // PreAuthorize抛出的AccessDeniedException异常被@ControlleAdvice注解的全局异常处理器拦截，没有进入RestfulAccessDeniedHandler
    //@PreAuthorize("hasAuthority('admin')")
    public ResultVO index(@RequestParam(value = "p", defaultValue = "1") int p,
                          @RequestParam(value = "s", defaultValue = "30") int s,
                          @RequestParam(value = "searchValue", required = false) String searchValue){
        ResultVO resultVO = new ResultVO();
        
        Long userId = tokenService.getUserId();
        System.out.println(userId);
        
        try {
            Map<String, Object> allStudentByLimit = studentService.getAllStudentByLimit(searchValue,p, s);
            resultVO.getResult().put("list", allStudentByLimit.get("list"));
            resultVO.getResult().put("total", allStudentByLimit.get("total"));
            resultVO.getResult().put("now", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            resultVO.getResult().put("token", JwtTokenUtil.generateExpirationDate());
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
    
    @PutMapping("/{id}")
    public ResultVO update(@PathVariable(value = "id") Long studentId,
                           @RequestParam(value = "studentName", required = false) String studentName,
                           @RequestParam(value = "studentCode", required = false) String studentCode){
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(1);
        try {
            studentService.updateStudent(studentId, studentName, studentCode);
            resultVO.getResult().put("id", studentId);
        } catch (Exception e){
            resultVO = resultVO.failure();
            log.error("update student fail, message:",e);
        }
        
        return resultVO;
    }
}
