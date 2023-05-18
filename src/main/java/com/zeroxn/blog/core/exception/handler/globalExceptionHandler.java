package com.zeroxn.blog.core.exception.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.zeroxn.blog.core.exception.CustomException;
import com.zeroxn.blog.core.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ran
 * @date
 * 全局异常处理器
 */
@ControllerAdvice
@Slf4j
public class globalExceptionHandler {
    /**
     * 处理自定义异常
     * @param ex 自定义异常抛出的异常消息
     * @return 返回给前端的错误消息
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result<String> handlerCustomException(CustomException ex){
        log.warn("CustomException抛出错误：" + ex);
        return Result.error(ex.getMessage());
    }

    /**
     * 处理Sa-token的未登录异常
     * @param ex 抛出的异常消息
     * @return 返回给前端的错误消息
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public Result<String> handlerNotLoginException(NotLoginException ex){
        log.warn("NotLoginException抛出错误：" + ex);
        return Result.custom(1001, "未登录");
    }

    /**
     * 处理数据库异常
     * @param ex 抛出的异常消息
     * @return
     */
    @ExceptionHandler(PSQLException.class)
    @ResponseBody
    public Result<String> handlerPSQLException(PSQLException ex){
        String errorMessage = ex.getMessage();
        log.warn("PSQLException抛出错误：" + ex.getMessage());
        //如果是字段的值重复了 那么就获取这个重复字段的值 然后返回给前端
        if (errorMessage.contains("duplicate key value violates unique constraint")){
            int startIndex = errorMessage.lastIndexOf("(") + 1;
            int endIndex = errorMessage.lastIndexOf(")");
            String errorName = errorMessage.substring(startIndex, endIndex);
            return Result.error(errorName + "重复了");
        }
        //否则直接返回错误
        return Result.error("出错了！");
    }
}
