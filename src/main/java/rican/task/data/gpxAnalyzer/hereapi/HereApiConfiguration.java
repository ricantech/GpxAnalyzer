package rican.task.data.gpxAnalyzer.hereapi;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class HereApiConfiguration {
    @Bean
    public RestTemplate hereApiRestTemplate(RestTemplateBuilder builder, HereApiProperties properties) {
        return builder.uriTemplateHandler(new DefaultUriBuilderFactory(properties.getUrl())).build();
    }
}