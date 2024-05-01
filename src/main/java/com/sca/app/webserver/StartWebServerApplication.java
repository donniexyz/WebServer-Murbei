package com.sca.app.webserver;

import com.sca.app.webserver.lib.WebServer;

class StartWebServerApplication {
    public static void main(String[] args)  {
        int port = 8080;
        WebServer server = new WebServer(port);
        server.start();
    }
}
