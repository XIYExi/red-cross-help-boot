package com.red.rpc.core.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.red.rpc.core.model.RpcRequest;
import com.red.rpc.core.model.RpcResponse;

import java.io.IOException;


public class JsonSerializer implements Serializer{

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public <T> byte[] serializer(T object) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }

    @Override
    public <T> T deserializer(byte[] bytes, Class<T> classType) throws IOException {
        T obj = OBJECT_MAPPER.readValue(bytes, classType);
        if (obj instanceof RpcRequest)
            return handleRequest((RpcRequest) obj, classType);
        if (obj instanceof RpcResponse)
            return handleResponse((RpcResponse) obj, classType);
        return obj;
    }


    /**
     * 由于Object的原始对象会被擦除，导致反序列化时会被作为LinkedHashMap无法转化为原始对象
     * @param rpcRequest
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    private <T> T handleRequest(RpcRequest rpcRequest, Class<T> type) throws IOException {
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();

        for (int i=0;i < parameterTypes.length; ++i) {
            Class<?> clazz = parameterTypes[i];
            if (!clazz.isAssignableFrom(args[i].getClass())) {
                byte[] argBytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(argBytes, clazz);
            }
        }
        return type.cast(rpcRequest);
    }


    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> type) throws IOException {
        byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());
        rpcResponse.setData(OBJECT_MAPPER.readValue(dataBytes, rpcResponse.getDataType()));
        return type.cast(rpcResponse);
    }

}
