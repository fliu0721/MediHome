package com.medihome.vo;

import lombok.Data;

/**
 * 统一响应结果VO
 */
@Data
public class ResultVO<T> {
    
    private Integer code;
    private String message;
    private T data;
    
    public static <T> ResultVO<T> success() {
        return success(null);
    }
    
    public static <T> ResultVO<T> success(T data) {
        ResultVO<T> result = new ResultVO<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }
    
    public static <T> ResultVO<T> error(String message) {
        return error(500, message);
    }
    
    public static <T> ResultVO<T> error(Integer code, String message) {
        ResultVO<T> result = new ResultVO<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
