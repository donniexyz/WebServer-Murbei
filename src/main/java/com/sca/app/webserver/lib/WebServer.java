package com.sca.app.webserver.lib;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

@Slf4j
public class WebServer {

    private final int port;

    public WebServer(int port) {
        this.port = port;
    }

    public void start() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor(); ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Server started at port {}", port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.execute(new RequestHandler(clientSocket));
            }
        } catch (IOException e) {
            log.error(" Exception occurred", e);
        }
    }
}

