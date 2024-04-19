package com.red.rpc.core.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.red.rpc.core.RpcApplication;
import com.red.rpc.core.model.RpcRequest;
import com.red.rpc.core.model.RpcResponse;
import com.red.rpc.core.serializer.JdkSerializer;
import com.red.rpc.core.serializer.Serializer;
import com.red.rpc.core.serializer.SerializerFactory;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 服务代理
 */
public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {
            // 序列化
            byte[] bodyBytes = serializer.serializer(rpcRequest);
            // 发送请求
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse response = serializer.deserializer(result, RpcResponse.class);
                return response.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
