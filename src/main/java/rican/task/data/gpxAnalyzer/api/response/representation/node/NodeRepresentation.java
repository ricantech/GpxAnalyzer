package rican.task.data.gpxAnalyzer.api.response.representation.node;

import rican.task.data.gpxAnalyzer.api.response.representation.BaseRepresentation;

import java.time.LocalDateTime;


public class NodeRepresentation extends BaseRepresentation {

    private Double latitude;
    private Double longitude;
    private Double elevation;
    private LocalDateTime timeOfRecordCreation;
    
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public LocalDateTime getTimeOfRecordCreation() {
        return timeOfRecordCreation;
    }

    public void setTimeOfRecordCreation(LocalDateTime timeOfRecordCreation) {
        this.timeOfRecordCreation = timeOfRecordCreation;
    }
}
