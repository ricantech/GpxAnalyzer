package rican.task.data.gpxAnalyzer.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.ResourceSupport;

@JsonPropertyOrder({"itemId"})
public class BaseRepresentation extends ResourceSupport {

    @JsonProperty("id")
    private Long itemId;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
}
