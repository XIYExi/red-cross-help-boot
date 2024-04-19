package com.red.rpc.core.server;

import com.red.rpc.core.RpcApplication;
import com.red.rpc.core.model.RpcRequest;
import com.red.rpc.core.model.RpcResponse;
import com.red.rpc.core.registry.LocalRegistry;
import com.red.rpc.core.serializer.JdkSerializer;
import com.red.rpc.core.serializer.Serializer;
import com.red.rpc.core.serializer.SerializerFactory;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Method;


/**
 * 1. 反序列化请求为对象，并从对象中获取参数
 * 2. 根据服务名从注册器中获取到对应的服务类实例
 * 3. 通过反射机制调用方法，得到返回结果
 * 4. 对返回结果进行封装和序列化，并写入响应体中
 */
@Slf4j
public class HttpServerHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest request) {
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        log.info("Received request: {} - {}", request.method(), request.uri());
        // 异步处理HTTP请求
        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserializer(bytes, RpcRequest.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();
            if (ObjectUtils.isEmpty(rpcRequest)) {
                rpcResponse.setMessage("RpcRequest is null");
                doResponse(request, rpcResponse, serializer);
                return;
            }

            try {
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                // 封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            // 响应
            doResponse(request, rpcResponse, serializer);
        });
    }

    private void doResponse(HttpServerRequest request, RpcResponse response, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response()
                .putHeader("content-type", "application/json");
        try {
            //序列化
            byte[] serialized = serializer.serializer(response);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
