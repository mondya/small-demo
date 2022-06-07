package com.xhh.smalldemo.controller;

import com.xhh.smalldemo.pojo.ImpRecord;
import com.xhh.smalldemo.service.imp.ImpService;
import com.xhh.smalldemo.vo.common.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/1.0/api/imp")
@Api(value = "excel导入导出", tags = "excel")
public class ImpRecordController {
    
    @Autowired
    ImpService impService;
    
    @ApiOperation(value = "查询记录")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
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
    
    @ApiOperation(value = "导入导出")
    @PostMapping("/save")
    public ResultVO saveImp(@RequestParam("type") Byte type, @RequestParam("id") Long id){
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(1);
        //TODO 需要增加type类型, id做重试使用,适用于某些导出失败需要重试操作的业务
        ImpRecord impRecord = new ImpRecord();
        try {
            impService.saveAndUpload(impRecord);
        } catch (Exception e){
            log.error("error,exception:",e);
            resultVO = resultVO.failure();
        }
        return resultVO;
    }
    
    @ApiOperation(value = "删除导出记录")
    @DeleteMapping("/delete/{id}")
    public ResultVO delete(@PathVariable("id") Long id){
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
