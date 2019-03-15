package rican.task.data.gpxAnalyzer.api.response.representation.path;

import rican.task.data.gpxAnalyzer.api.response.representation.BaseRepresentation;

import java.time.LocalDateTime;

public class PathRepresentation extends BaseRepresentation {

    private LocalDateTime created;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

}