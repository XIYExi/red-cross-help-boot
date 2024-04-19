package com.red.rpc.core.model;

import com.red.rpc.core.constant.RpcConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest {

    private String serviceName;

    private String methodName;

    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;

    private Class<?>[] parameterTypes;

    private Object[] args;
}
