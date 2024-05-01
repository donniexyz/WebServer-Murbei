package com.sca.app.webserver.lib;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

class HTTPResponse {
    private final OutputStream output;
    private final PrintWriter writer;

    public HTTPResponse(OutputStream output) {
        this.output = output;
        this.writer = new PrintWriter(output, true);
    }

    public void sendResponse(int status, String contentType, String body) throws IOException {
        byte[] bodyBytes = body.getBytes();
        writer.println("HTTP/1.1 " + status + " OK");
        writer.println("Content-Type: " + contentType);
        writer.println("Content-Length: " + bodyBytes.length);
        writer.println();
        writer.flush();
        output.write(bodyBytes, 0, bodyBytes.length);
        writer.close();
    }

}
