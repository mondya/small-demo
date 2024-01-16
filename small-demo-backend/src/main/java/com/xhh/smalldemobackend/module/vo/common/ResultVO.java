package com.xhh.smalldemobackend.module.vo.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class ResultVO implements Serializable {
    int status;
    int code;
    String message;
    Map<String, Object> result = new HashMap<>();

    public ResultVO success() {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(1);
        resultVO.setMessage("success");
        return resultVO;
    }

    public ResultVO failure() {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(0);
        resultVO.setMessage("failed");
        return resultVO;
    }
}
