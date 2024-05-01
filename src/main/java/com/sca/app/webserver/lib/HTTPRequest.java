package com.sca.app.webserver.lib;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Slf4j
class HTTPRequest {
    private final BufferedReader reader;
    private final Map<String, Object> headers = new HashMap<>();

    public HTTPRequest(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
        parseHeaders();
    }

    private void parseHeaders() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    break; // No more headers
                }
                String[] headerParts = line.split(": ", 2);
                if (headerParts.length == 2) {
                    headers.put(headerParts[0], headerParts[1]);
                }
            }
        } catch (IOException e) {
            log.error(" Exception occurred", e);
        }
    }

    private static Object putMultiValueMap(String key, Map<String, Object> map, String value) {
        Object val = map.get(key);
        return switch (val) {
            case null -> map.put(key, value);
            case String asString -> map.put(key, new ArrayList<>(List.of(asString, value)));
            case List asList -> asList.add(value);
            default -> map.put(key, new ArrayList<>(List.of(val, value)));
        };
    }

    public String getHeader(String headerName) {
        Object val = headers.get(headerName);
        return val instanceof List asList ? asList.get(0).toString() : null == val ? null : val.toString();
    }

    public List<String> getHeaders(String headerName) {
        Object val = headers.get(headerName);
        return val instanceof List asList ? asList : null == val ? null : List.of(val.toString());
    }

}
