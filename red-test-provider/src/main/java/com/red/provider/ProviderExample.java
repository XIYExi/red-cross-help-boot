package com.red.provider;

import com.red.rpc.core.RpcApplication;
import com.red.rpc.core.registry.LocalRegistry;
import com.red.rpc.core.server.HttpServer;
import com.red.rpc.core.server.VertxHttpServer;

public class ProviderExample {
    public static void main(String[] args) {
        RpcApplication.init();

        // LocalRegistry.register(UserService.class.getName(), UserSreviceImpl.class);
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
