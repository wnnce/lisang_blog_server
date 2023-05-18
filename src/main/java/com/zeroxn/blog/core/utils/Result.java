package com.zeroxn.blog.core.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ran
 * @date
 * @param <T>
 */
@Data
public class Result<T> {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 固定的需要返回的信息
     */
    private Info info;
    /**
     * 返回数据
     */
    private T data;
    //每次创建Result时 都自动设置info属性
    public Result(){
        setInfo(new Info());
    }
    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.setCode(1);
        result.setData(data);
        return result;
    }
    public static <T> Result<T> error(String message){
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> custom(Integer code, String message){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 内部类
     */
    @Data
    private static class Info {
        public Info(){}
        private final String site = "离桑的博客";
        private final String url = "https://blog.zeroxn.com";
        private final LocalDateTime date = LocalDateTime.now();
    }
}
