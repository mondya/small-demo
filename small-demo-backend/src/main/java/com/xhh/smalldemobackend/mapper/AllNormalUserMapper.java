package com.xhh.smalldemobackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhh.smalldemocommon.pojo.AllNormalUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AllNormalUserMapper extends BaseMapper<AllNormalUser> {
    
    List<AllNormalUser> findAll();
}
