package com.xhh.smalldemo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

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
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
    
    // 建造者模式
    public static class Builder{
        private Long campusId;
        private String name;
        private String code;
        private Byte status;
        private LocalDateTime dateCreated;
        private LocalDateTime lastUpdated;
        
        
        
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
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", campusId=" + campusId +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", status=" + status +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
