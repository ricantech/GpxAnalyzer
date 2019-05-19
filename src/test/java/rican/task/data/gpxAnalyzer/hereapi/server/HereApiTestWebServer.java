package rican.task.data.gpxAnalyzer.hereapi.server;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import rican.task.data.gpxAnalyzer.hereapi.HereApiProperties;

import javax.annotation.PreDestroy;
import java.io.IOException;

public class HereApiTestWebServer {

    private static MockWebServer server = new MockWebServer();

    public HereApiTestWebServer(HereApiProperties properties) {
        int serverPort = parsePort(properties.getUrl());
        try {
            server.play(serverPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void mockResponse(MockResponse mockResponse) {
        server.enqueue(mockResponse);
    }

    private int parsePort(String url) {
        int portSeparatorIndex = url.lastIndexOf(":");
        return Integer.valueOf(url.substring(portSeparatorIndex + 1).replace("/", ""));
    }

    @PreDestroy
    public void shutdown() {
        try {
            server.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}