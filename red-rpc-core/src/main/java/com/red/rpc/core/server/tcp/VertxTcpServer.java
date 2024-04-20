package com.red.rpc.core.server.tcp;

import com.red.rpc.core.server.HttpServer;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;

public class VertxTcpServer implements HttpServer {


    private byte[] handleRequest(byte[] requestData){
        return "Hello, client!".getBytes();
    }


    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();

        NetServer server = vertx.createNetServer();

        // handle request
        server.connectHandler(socket -> {
            // handle connection
            socket.handler(buffer -> {
                byte[] requestData = buffer.getBytes();
                byte[] responseData = handleRequest(requestData);
                // send response
                socket.write(Buffer.buffer(responseData));
            });
        });

        // start Tcp Server and listen port
        server.listen(port, result -> {
            if (result.succeeded())
                System.out.println("TCP Server started on port " + port);
            else
                System.out.println("Failed to start TCP Server: " + result.cause());
        });
    }


    public static void main(String[] args) {
        new VertxTcpServer().doStart(8888);
    }
}
