package com.sca.app.webserver.lib;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
class RequestHandler implements Runnable {
    private final Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();

                HTTPRequest httpRequest = new HTTPRequest(input);
                HTTPResponse httpResponse = new HTTPResponse(output);
                new HelloWorldExampleHandler().handle(httpRequest, httpResponse);

                if (!isConnectionKeepAlive(httpRequest)) {
                    break;
                }
            }
        } catch (IOException e) {
            log.error("Exception occurred", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log.error("Exception occurred", e);
            }
        }
    }

    // keep alive is from header:  Connection: keep-alive
    private boolean isConnectionKeepAlive(HTTPRequest request) {
        String connectionFromHeader = request.getHeader("Connection");

        return connectionFromHeader != null && connectionFromHeader.equalsIgnoreCase("keep-alive");
    }
}
