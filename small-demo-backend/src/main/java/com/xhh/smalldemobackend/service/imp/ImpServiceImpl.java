package com.xhh.smalldemobackend.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhh.smalldemobackend.mapper.ImpRecordMapper;
import com.xhh.smalldemocommon.pojo.ImpRecord;
import com.xhh.smalldemocommon.utils.ToStringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImpServiceImpl extends ServiceImpl<ImpRecordMapper, ImpRecord> implements ImpService {
    
    @Autowired
    ImpRecordMapper impRecordMapper;
    
    @Override
    public Map<String, Object> getAllImpRecordByLimit(Byte type, int p, int s) {
        Map<String, Object> map = new HashMap<>();
        Page<ImpRecord> page = new Page<>(p, s);
        IPage<ImpRecord> pageList = impRecordMapper.selectPage(page, new QueryWrapper<ImpRecord>().eq("type", type).eq("status",(byte)1));
        map.put("list", pageList.getRecords());
        map.put("total", pageList.getTotal());
        return map;
    }

    @Override
    @Async
    public void saveAndUpload(ImpRecord impRecord) {
        impRecord.setFileName("hello");
        this.saveImpRecord(impRecord);
    }

    @Override
    public void deleteRecord(Long id, String ids) {
        ImpRecord impRecord = impRecordMapper.selectById(id);
        if (ObjectUtils.isNotEmpty(impRecord)){
            impRecord.setStatus((byte)0);
            impRecord.setLastUpdated(LocalDateTime.now());
            impRecordMapper.updateById(impRecord);
        }
        // 批量删除
        if (StringUtils.isNotBlank(ids)){
            List<Long> idList = ToStringUtils.stringIdsToListLong(ids);
            List<ImpRecord> impRecords = impRecordMapper.selectBatchIds(idList);
            impRecords.forEach((ImpRecord imp) -> {
                imp.setStatus((byte) 0);
                imp.setLastUpdated(LocalDateTime.now());
            });
            super.saveOrUpdateBatch(impRecords);
        }
    }

    @Transactional
    void saveImpRecord(ImpRecord impRecord){
        impRecordMapper.insert(impRecord);
    }
}
