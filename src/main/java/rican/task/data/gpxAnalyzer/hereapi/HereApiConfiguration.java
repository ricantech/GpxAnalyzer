package rican.task.data.gpxAnalyzer.hereapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HereApiConfiguration {
    @Bean
    public WebClient hereApiWebClient(WebClient.Builder builder, HereApiProperties properties) {
        return builder.baseUrl(properties.getUrl()).build();
    }
}