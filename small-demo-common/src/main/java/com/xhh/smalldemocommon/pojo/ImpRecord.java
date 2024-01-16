package com.xhh.smalldemocommon.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("imp_record")
@ApiModel(value = "导入导出相关类")
public class ImpRecord implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    //导入导出类型
    private Byte type;
    //报错信息，冗余
    private String message;
    private String fileName;
    private String url;
    private String userName;
    private Long userId;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
    private Byte status;
}
