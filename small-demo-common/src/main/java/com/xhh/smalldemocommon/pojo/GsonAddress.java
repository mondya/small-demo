package com.xhh.smalldemocommon.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GsonAddress {
    private String province;
    private String city;
    private String area;
}
