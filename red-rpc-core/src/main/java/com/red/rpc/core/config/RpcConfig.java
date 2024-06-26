package com.red.rpc.core.config;

import com.red.rpc.core.fault.retry.RetryStrategyKeys;
import com.red.rpc.core.loadbalancer.LoadBalancerKeys;
import com.red.rpc.core.registry.RegistryKeys;
import com.red.rpc.core.serializer.SerializerKeys;
import lombok.Data;

@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "red-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 模拟调用
     */
    private boolean mock = false;


    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;


    /**
     * 注册中心
     */
    private RegistryConfig registryConfig = new RegistryConfig();


    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;


    /**
     * 重试策略
     */
    private String retryStrategy = RetryStrategyKeys.NO;
}
