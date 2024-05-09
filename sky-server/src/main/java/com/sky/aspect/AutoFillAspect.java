package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @projectName: sky-take-out
 * @package: com.sky.aspect
 * @className: AutoFillAspect
 * @author: 姬紫衣
 * @description: 自定义切面，实现公共字段自动填充处理
 * @date: 2024/5/9 13:14
 * @version: 1.0
 */

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点
     */

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始公共字段自动填充");

        //获取当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获取方法上的注解对象
        OperationType operationType = autoFill.value();//获取数据库操作类型

        //获取当前被拦截的方法参数--实体类
        Object[] args = joinPoint.getArgs();
        if(args == null && args.length == 0)
        {
            return;
        }
        Object entity = args[0];//获取到参数（实体）

        //准备赋值参数
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //根据不同操作类型赋值
        if(operationType == OperationType.INSERT)
        {
            //为四个公共字段赋值
            Method setCreatTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setCreatUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);


            //赋值
            setCreatTime.invoke(entity , now);
            setCreatUser.invoke(entity , currentId);
            setUpdateTime.invoke(entity , now);
            setUpdateUser.invoke(entity , currentId);
        }
        if(operationType == OperationType.UPDATE)
        {
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

            setUpdateTime.invoke(entity , now);
            setUpdateUser.invoke(entity , currentId);
        }

    }

}
