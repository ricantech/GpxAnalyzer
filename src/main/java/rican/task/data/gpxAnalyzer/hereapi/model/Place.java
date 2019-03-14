package rican.task.data.gpxAnalyzer.hereapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Optional.ofNullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {

    @JsonProperty("distance")
    private Double distance;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("category")
    private Category category;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryTitle() {
        return ofNullable(category).map(Category::getTitle).orElse(null);
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
