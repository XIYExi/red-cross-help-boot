package com.red.rpc.core.model;

/**
 * 服务元信息（注册信息）
 */
public class ServiceMetaInfo {
    private String serviceName;

    private String serviceVersion = "1.0";

    private String serviceAddress;

    private String serviceGroup = "default";

    public String getServiceKey() {
        return String.format("%s%:%s", serviceName, serviceVersion);
    }

    public String getServiceNodeKey() {
        return String.format("%s/%s", getServiceKey(), serviceAddress);
    }
}
