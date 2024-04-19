package com.red.rpc.core.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer{
    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();

        io.vertx.core.http.HttpServer server = vertx.createHttpServer();
        server.requestHandler(new HttpServerHandler());

        server.requestHandler(request -> {
            System.out.println("Received request: " + request.method());

            request.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x HTTP Server!");
        });
        server.listen(port, result -> {
            if (result.succeeded()){
                System.out.println("Server is now listening on port: " + port);
            }
            else {
                System.out.println("Failed to start server: " + result.cause());
            }
        });
    }
}
