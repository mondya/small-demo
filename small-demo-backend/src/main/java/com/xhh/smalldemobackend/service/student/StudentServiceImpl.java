package com.xhh.smalldemobackend.service.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhh.smalldemobackend.event.myEvent.MyEvent;
import com.xhh.smalldemobackend.event.myEvent.msg.DingMsgEvent;
import com.xhh.smalldemobackend.event.myEvent.msg.WxMsgEvent;
import com.xhh.smalldemobackend.event.publish.MyPublisher;
import com.xhh.smalldemobackend.mapper.StudentMapper;
import com.xhh.smalldemobackend.module.dto.DingMsgDTO;
import com.xhh.smalldemobackend.module.dto.WxMsgDTO;
import com.xhh.smalldemocommon.pojo.Student;
import com.xhh.smalldemocommon.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
//@DS("slave_1")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService{
    
    @Resource
    StudentMapper studentMapper;
    
    @Resource
    MyPublisher myPublisher;
    
    @Override
    public Map<String,Object> getAllStudentByLimit(String searchValue, int p, int s) {
        Map<String, Object> map = new HashMap<>();
        Page<Student> page = new Page<Student>(p,s);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
        queryWrapper.eq("status", (byte)1);
        if (StringUtils.isNotBlank(searchValue)){
            // 添加一个括号内查询
            // queryWrapper.and( i -> i.like("name", searchValue).or().like("code", searchValue));
            queryWrapper.and(i -> i.
                     or( j -> j.like("name", searchValue))
                    .or(j -> j.like("code", searchValue))
                    .or(k -> k.like("id", searchValue))
            );
        }
        IPage<Student> iPage = studentMapper.selectPage(page, queryWrapper);
        map.put("list", iPage.getRecords());
        map.put("total", iPage.getTotal());
        return map;
    }

    @Override
    public void insertOneStudent(String code, String name) {
        Student student = new Student.Builder().code(code).name(name).status((byte)1).build();
        
        try {
            MyEvent myEvent = new MyEvent(this, name);
            myPublisher.publishEvent(myEvent);
            
            
            DingMsgDTO dingMsgDTO = new DingMsgDTO();
            dingMsgDTO.setDingUserId("ding_user_id");

            DingMsgEvent dingMsgEvent = new DingMsgEvent(this, dingMsgDTO);
            myPublisher.publishDingMsgEvent(dingMsgEvent);
            
            WxMsgDTO wxMsgDTO = new WxMsgDTO();
            wxMsgDTO.setOpenId("open_id");

            WxMsgEvent wxMsgEvent = new WxMsgEvent(this, wxMsgDTO);
            myPublisher.publishWxMsgEvent(wxMsgEvent);
        } catch (Exception e) {
            log.error("[studentServiceImpl] publish event fail, e:", e);
        }
        this.saveStudent(student);
    }

    @Override
    public Student getOne(Long studentId) {
        return studentMapper.selectById(studentId);
    }

    @Override
    public void updateStudent(Long studentId, String name, String code) {
        Student student = studentMapper.selectById(studentId);
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("code", code);
        if (ObjectUtil.checkChange(student, map)){
            student.setName(name);
            student.setCode(code);
            this.updateStudent(student);
        }
    }

    @Override
//    @Transactional
    public void insertOne(Student student) {
        studentMapper.insert(student);
        throw new RuntimeException("事务");
    }

    @Transactional(rollbackFor = Exception.class)
    void saveStudent(Student student){
        studentMapper.insert(student);
    }
    
    @Transactional(rollbackFor = Exception.class)
    void updateStudent(Student student){
        studentMapper.updateById(student);
    }
}
