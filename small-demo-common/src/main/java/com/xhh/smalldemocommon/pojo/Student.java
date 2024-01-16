package com.xhh.smalldemocommon.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel(value = "student", description = "学生表")
@TableName("student")
@AllArgsConstructor
@Data
public class Student {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long campusId;
    private String name;
    private String code;
    private Byte status;
    private Long timeStamp;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime dateCreated;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastUpdated;
    
    private BigDecimal score;
    
    // 建造者模式
    public static class Builder{
        private Long campusId;
        private String name;
        private String code;
        private Byte status;
        private Long timeStamp;
        private LocalDateTime dateCreated;
        private LocalDateTime lastUpdated;
        private BigDecimal score;
        
        
        
        public Builder campusId(Long campusId){
            this.campusId = campusId;
            return this;
        }
        
        public Builder name(String name){
            this.name = name;
            return this;
        }
        
        public Builder code(String code){
            this.code = code;
            return this;
        }

        public Builder status(Byte status){
            this.status = status;
            return this;
        }
        
        public Builder timeStamp(Long timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder dateCreated(LocalDateTime dateCreated){
            this.dateCreated = dateCreated;
            return this;
        }

        public Builder lastUpdated(LocalDateTime lastUpdated){
            this.lastUpdated = lastUpdated;
            return this;
        }
        
        public Student build(){
            return new Student(this);
        }
    }
    
    private Student(Builder builder){
        campusId = builder.campusId;
        name = builder.name;
        code = builder.code;
        status = builder.status;
        dateCreated = builder.dateCreated;
        lastUpdated = builder.lastUpdated;
        timeStamp = builder.timeStamp;
        score = builder.score;
    }
    
}
