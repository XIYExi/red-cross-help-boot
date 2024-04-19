package com.red.rpc.core.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.red.rpc.core.RpcApplication;
import com.red.rpc.core.config.RegistryConfig;
import com.red.rpc.core.config.RpcConfig;
import com.red.rpc.core.constant.RpcConstant;
import com.red.rpc.core.model.RpcRequest;
import com.red.rpc.core.model.RpcResponse;
import com.red.rpc.core.model.ServiceMetaInfo;
import com.red.rpc.core.registry.Registry;
import com.red.rpc.core.registry.RegistryFactory;
import com.red.rpc.core.serializer.JdkSerializer;
import com.red.rpc.core.serializer.Serializer;
import com.red.rpc.core.serializer.SerializerFactory;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

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


            // 从注册中心获取服务提供者的请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(method.getDeclaringClass().getName());
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            // 暂时获取第一个
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);

            // 发送请求
            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
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
