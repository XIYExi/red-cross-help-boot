package com.red.common.component;


import com.alibaba.fastjson.JSON;
import com.red.common.pojo.SysLog;
import com.red.common.service.impl.SysLogServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Aspect
@Slf4j
@Component
public class LogAspect {

    @Autowired
    @Qualifier("sysLogServiceImpl")
    private SysLogServiceImpl sysLogService;

    @Pointcut("@annotation(com.red.common.component.Log)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        SysLog sysLog = this.getMethodInfo(joinPoint);
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis();
        sysLog.setCreateTime(new Date())
                .setExecutionTime(end - start);
        // log.info(JSON.toJSONString(sysLog));
        sysLog.setUserId("1");
        // 日志存数据库
        sysLogService.save(sysLog);
        return proceed;
    }


    private SysLog getMethodInfo(ProceedingJoinPoint joinPoint) {
        SysLog sysLog = new SysLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log customLog = method.getAnnotation(Log.class);
        // 注解上的描述
        Optional.ofNullable(customLog).ifPresent(c -> sysLog.setDescription(c.value()));
        try {
            sysLog
                    .setMethodName(joinPoint.getSignature().getName())
                    .setPackageName(joinPoint.getTarget().getClass().getName())
                    .setParams(JSON.toJSONString(this.getParameters(joinPoint)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysLog;
    }

    private Map<String, Object> getParameters(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        Map<String, Object> paramsMap = new HashMap<>(2);
        for (int i = 0; i < parameterValues.length; i++) {
            try {
                Object s = parameterValues[i];
                paramsMap.put(parameterNames[i], s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return paramsMap;
    }

}
