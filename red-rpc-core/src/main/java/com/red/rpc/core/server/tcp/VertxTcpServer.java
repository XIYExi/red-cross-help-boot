package com.red.rpc.core.server.tcp;

import com.red.rpc.core.server.HttpServer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            RecordParser parser = RecordParser.newFixed(8);
            parser.setOutput(new Handler<Buffer>() {
                int size = -1;
                Buffer resultBuffer = Buffer.buffer();

                @Override
                public void handle(Buffer buffer) {
                    if (-1 == size) {
                        size = buffer.getInt(4);
                        parser.fixedSizeMode(size);
                        resultBuffer.appendBuffer(buffer);
                    }
                    else {
                        resultBuffer.appendBuffer(buffer);
                        System.out.println(resultBuffer.toString());
                        parser.fixedSizeMode(8);
                        size = -1;
                        resultBuffer = Buffer.buffer();
                    }
                }
            });
            // handle connection
            socket.handler(parser);
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
