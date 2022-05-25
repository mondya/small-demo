package com.xhh.smalldemo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("imp_record")
public class ImpRecord implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fileName;
    private String url;
    private String userName;
    private Long userId;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
    private Byte status;
}
