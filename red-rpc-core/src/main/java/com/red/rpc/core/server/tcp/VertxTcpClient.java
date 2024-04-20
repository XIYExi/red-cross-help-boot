package com.red.rpc.core.server.tcp;

import io.vertx.core.Vertx;

import java.nio.charset.StandardCharsets;

public class VertxTcpClient {
    public void start() {
        Vertx vertx = Vertx.vertx();

        vertx.createNetClient().connect(8888, "localhost", result -> {
            if (result.succeeded()) {
                System.out.println("Connected to TCP server...");
                io.vertx.core.net.NetSocket socket = result.result();
                // send message
                socket.write("Hello server!");
                // get response
                socket.handler(buffer -> {
                    System.out.println("Received response from server: " + buffer.toString(StandardCharsets.UTF_8));
                });
            }
            else {
                System.out.println("Failed to connect to TCP server");
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpClient().start();
    }
}
