package rican.task.data.gpxAnalyzer.hereapi.server;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import rican.task.data.gpxAnalyzer.hereapi.HereApiProperties;

@TestConfiguration
public class HereApiTestWebServerConfiguration {

    @Bean
    public HereApiTestWebServer hereApiTestWebServer(HereApiProperties hereApiProperties) {
        return new HereApiTestWebServer(hereApiProperties);
    }
}