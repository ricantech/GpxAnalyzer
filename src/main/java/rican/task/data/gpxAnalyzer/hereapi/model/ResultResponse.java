package rican.task.data.gpxAnalyzer.hereapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultResponse {
    
    @JsonProperty("results")
    private Places results;
    
    public List<Place> getItems() {
        return ofNullable(results).map(Places::getItems).orElseGet(Collections::emptyList);
    }

    public void setResults(Places results) {
        this.results = results;
    }
}