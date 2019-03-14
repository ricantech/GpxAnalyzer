package rican.task.data.gpxAnalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rican.task.data.gpxAnalyzer.hereapi.HereApiProperties;

@SpringBootApplication
@EnableConfigurationProperties(HereApiProperties.class)
public class GpxAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpxAnalyzerApplication.class, args);
	}

}
