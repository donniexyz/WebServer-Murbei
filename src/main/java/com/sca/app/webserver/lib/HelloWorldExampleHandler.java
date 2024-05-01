package com.sca.app.webserver.lib;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class HelloWorldExampleHandler implements Handler {
    @Override
    public void handle(HTTPRequest request, HTTPResponse response) {
        try {
            response.sendResponse(200, "Content-Type: text/plain", "Example: Hello, World!");
        } catch (IOException e) {
            log.error("  Exception occurred", e);
        }
    }
}
