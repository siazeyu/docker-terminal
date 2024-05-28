package com.szy.terminal.web.entity.system;

import com.alibaba.fastjson.JSON;
import lombok.Data;


@Data
public class ResultData {
    /**
     * 结果状态 ,具体状态码参见ResultData.java
     */
    private int status;
    private String message;
    private Object data;
    private long timestamp;


    public ResultData() {
        this.timestamp = System.currentTimeMillis();
    }


    public static ResultData success(Object data) {
        ResultData resultData = new ResultData();
        resultData.setStatus(ReturnCode.RC100.getCode());
        resultData.setMessage(ReturnCode.RC100.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public static ResultData fail(int code, String message) {
        ResultData resultData = new ResultData();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
