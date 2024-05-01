package com.sca.app.webserver.lib;

interface Handler {
    void handle(HTTPRequest request, HTTPResponse response);
}
