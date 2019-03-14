package rican.task.data.gpxAnalyzer.controller.response;

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