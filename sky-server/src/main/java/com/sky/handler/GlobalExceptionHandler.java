package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 处理SQL异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex)
    {
        // Duplicate entry 'zhangsan' for key 'employee.idx_username'

        //获取异常信息
        String message = ex.getMessage();
        //判断异常信息类别
        if(message.contains("Duplicate entry"))
        {
            //分割异常信息
            String[] split = message.split(" ");
            //拿到动态异常信息username
            String username = split[2];
            //拼接异常信息提示
            String mesg = username + MessageConstant.ALREADY_EXISTS;
            //返回给前端
            return Result.error(mesg);
        }else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
