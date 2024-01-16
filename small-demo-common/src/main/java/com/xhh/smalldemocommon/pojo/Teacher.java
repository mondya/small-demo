package com.xhh.smalldemocommon.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "教师表")
@TableName(value = "teacher")
@Data
public class Teacher {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String code;
    private String mobile;

    private Teacher(Builder builder) {
        id = builder.id;
        name = builder.name;
        code = builder.code;
        mobile = builder.mobile;
    }

    public static final class Builder {
        private Long id;
        private String name;
        private String code;
        private String mobile;

        public Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder code(String val) {
            code = val;
            return this;
        }

        public Builder mobile(String val) {
            mobile = val;
            return this;
        }

        public Teacher build() {
            return new Teacher(this);
        }
    }
}
