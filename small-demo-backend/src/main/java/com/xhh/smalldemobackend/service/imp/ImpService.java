package com.xhh.smalldemobackend.service.imp;

import com.xhh.smalldemocommon.pojo.ImpRecord;

import java.util.Map;

public interface ImpService {
    
    Map<String,Object> getAllImpRecordByLimit(Byte type, int p, int s);
    
    void saveAndUpload(ImpRecord impRecord);
    
    void deleteRecord(Long id, String ids);
}
