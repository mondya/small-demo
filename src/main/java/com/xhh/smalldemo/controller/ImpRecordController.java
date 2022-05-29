package com.xhh.smalldemo.controller;

import com.xhh.smalldemo.pojo.ImpRecord;
import com.xhh.smalldemo.service.imp.ImpService;
import com.xhh.smalldemo.vo.common.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.analysis.function.Exp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/1.0/api/imp")
public class ImpRecordController {
    
    @Autowired
    ImpService impService;
    
    @RequestMapping("/index")
    public ResultVO getAllImpRecord(@RequestParam(name = "p", defaultValue = "1") int p,
                                    @RequestParam(name = "s", defaultValue = "30") int s){
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(1);
        try {
            Map<String, Object> allImpRecordByLimit = impService.getAllImpRecordByLimit(p, s);
            resultVO.getResult().put("list", allImpRecordByLimit.get("list"));
            resultVO.getResult().put("total", allImpRecordByLimit.get("total"));
        } catch (Exception e){
            resultVO.setStatus(0);
            resultVO.setMessage("error");
            log.error("error, exception:", e);
        }
        return resultVO;
    }
    
    @PostMapping("/save")
    public ResultVO saveImp(@RequestParam("type") Byte type){
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(1);
        ImpRecord impRecord = new ImpRecord();
        try {
            impService.saveAndUpload(impRecord);
        } catch (Exception e){
            log.error("error,exception:",e);
            resultVO = resultVO.failure();
        }
        return resultVO;
    }
    
    @DeleteMapping("/delete/{id}")
    public ResultVO delete(@RequestParam("id") Long id){
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(1);
        try {
            impService.deleteRecord(id);
            resultVO.setMessage("success");
        } catch (Exception e){
            resultVO = resultVO.failure();
            log.error("delete error , exception:",e);
        }
        return resultVO;
    }
}
