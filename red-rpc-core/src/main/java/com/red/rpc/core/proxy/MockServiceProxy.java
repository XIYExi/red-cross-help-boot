package com.red.rpc.core.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Mock服务代理
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 根据方法的返回值类型，生成特定的默认对象
        Class<?> methodReturnType = method.getReturnType();
        log.info("mock invoke {}", method.getName());
        return getDefaultObject(methodReturnType);
    }


    private Object getDefaultObject(Class<?> methodReturnType) {
        // 基本类型
        if (methodReturnType.isPrimitive()) {
            if (methodReturnType == boolean.class)
                return false;
            else if (methodReturnType == short.class)
                return (short) 0;
            else if (methodReturnType == int.class)
                return 0;
            else if (methodReturnType == long.class)
                return 0L;
        }
        // 对象
        return null;
    }
}
