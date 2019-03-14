package rican.task.data.gpxAnalyzer.hereapi;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hereapi")
public class HereApiProperties {

    private String url;
    private String id;
    private String code;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
}