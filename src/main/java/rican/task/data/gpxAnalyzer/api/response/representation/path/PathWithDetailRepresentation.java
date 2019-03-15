package rican.task.data.gpxAnalyzer.api.response.representation.path;

import rican.task.data.gpxAnalyzer.api.response.representation.node.NodeRepresentation;
import rican.task.data.gpxAnalyzer.api.response.representation.node.NodeWithClosePlacesRepresentation;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;


public class PathWithDetailRepresentation extends PathRepresentation {

    private NodeRepresentation startNode;
    private NodeWithClosePlacesRepresentation lastNode;
    private Double averageSpeedKmPerHour;
    private Double startNodeEndNodeDistanceInMeters;
    private Double totalDistanceInMeters;
    private List<NodeRepresentation> pathDetails;

    public PathWithDetailRepresentation(PathRepresentation pathRepresentation) {
        copyProperties(pathRepresentation, this);
    }

    public NodeRepresentation getStartNode() {
        return startNode;
    }

    public void setStartNode(NodeRepresentation startNode) {
        this.startNode = startNode;
    }

    public NodeWithClosePlacesRepresentation getLastNode() {
        return lastNode;
    }

    public void setLastNode(NodeWithClosePlacesRepresentation lastNode) {
        this.lastNode = lastNode;
    }

    public Double getAverageSpeedKmPerHour() {
        return averageSpeedKmPerHour;
    }

    public void setAverageSpeedKmPerHour(Double averageSpeedKmPerHour) {
        this.averageSpeedKmPerHour = averageSpeedKmPerHour;
    }

    public Double getStartNodeEndNodeDistanceInMeters() {
        return startNodeEndNodeDistanceInMeters;
    }

    public void setStartNodeEndNodeDistanceInMeters(Double startNodeEndNodeDistanceInMeters) {
        this.startNodeEndNodeDistanceInMeters = startNodeEndNodeDistanceInMeters;
    }

    public Double getTotalDistanceInMeters() {
        return totalDistanceInMeters;
    }

    public void setTotalDistanceInMeters(Double totalDistanceInMeters) {
        this.totalDistanceInMeters = totalDistanceInMeters;
    }

    public List<NodeRepresentation> getPathDetails() {
        return pathDetails;
    }

    public void setPathDetails(List<NodeRepresentation> pathDetails) {
        this.pathDetails = pathDetails;
    }
}