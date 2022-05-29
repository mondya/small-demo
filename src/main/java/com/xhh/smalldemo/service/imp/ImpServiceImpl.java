package com.xhh.smalldemo.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhh.smalldemo.mapper.ImpRecordMapper;
import com.xhh.smalldemo.pojo.ImpRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImpServiceImpl implements ImpService {
    
    @Autowired
    ImpRecordMapper impRecordMapper;
    
    @Override
    public Map<String, Object> getAllImpRecordByLimit(int p, int s) {
        Map<String, Object> map = new HashMap<>();
        Page<ImpRecord> page = new Page<>(p, s);
        IPage<ImpRecord> pageList = impRecordMapper.selectPage(page, new QueryWrapper<ImpRecord>().gt("status", 0));
        map.put("list", pageList.getRecords());
        map.put("total", pageList.getTotal());
        return map;
    }

    @Override
    public void saveAndUpload(ImpRecord impRecord) {
        impRecord.setFileName("hello");
    }

    @Override
    public void deleteRecord(Long id) {
        ImpRecord impRecord = impRecordMapper.selectById(id);
        if (ObjectUtils.isNotEmpty(impRecord)){
            impRecord.setStatus((byte)0);
            impRecord.setLastUpdated(LocalDateTime.now());
            impRecordMapper.updateById(impRecord);
        }
    }

    @Transactional
    void saveImpRecord(ImpRecord impRecord){
        impRecordMapper.insert(impRecord);
    }
}
