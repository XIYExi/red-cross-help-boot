package com.red.rpc.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcResponse {

    private Object data;

    private Class<?> dataType;

    private String message;

    private Exception exception;
}
