package com.xhh.smalldemo.service.imp;

import com.xhh.smalldemo.pojo.ImpRecord;

import java.util.Map;

public interface ImpService {
    
    Map<String,Object> getAllImpRecordByLimit(int p, int s);
    
    void saveAndUpload(ImpRecord impRecord);
    
    void deleteRecord(Long id);
}
