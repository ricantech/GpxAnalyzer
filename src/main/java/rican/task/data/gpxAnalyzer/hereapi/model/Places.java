package rican.task.data.gpxAnalyzer.hereapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Places {

    @JsonProperty("items")
    private List<Place> items;

    public List<Place> getItems() {
        return items;
    }

    public void setItems(List<Place> items) {
        this.items = items;
    }
}
