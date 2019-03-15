package rican.task.data.gpxAnalyzer.domain.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table
public class Path {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "path_seq")
    @SequenceGenerator(name = "path_seq", sequenceName = "path_seq", allocationSize = 1)
    private Long id;

    @Column
    private LocalDateTime created;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "start_node_id")
    private Node startNode;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "last_node_id")
    private Node lastNode;

    @OneToMany(cascade = ALL, orphanRemoval = true, fetch = LAZY, mappedBy = "path")
    private List<Node> nodes = new ArrayList<>();

    @Column(name = "total_distance")
    private Double totalDistance;

    @Column(name = "start_node_end_node_distance")
    private Double startNodeEndNodeDistance;

    @Column(name = "average_speed_km_per_hour")
    private Double averageSpeedKmPerHour;

    public Long getId() {
        return id;
    }

    public Path() {
        this.created = now();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getLastNode() {
        return lastNode;
    }

    public void setLastNode(Node lastNode) {
        this.lastNode = lastNode;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        nodes.forEach(node -> node.setPath(this));
        this.nodes = nodes;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Double getStartNodeEndNodeDistance() {
        return startNodeEndNodeDistance;
    }

    public void setStartNodeEndNodeDistance(Double startNodeEndNodeDistance) {
        this.startNodeEndNodeDistance = startNodeEndNodeDistance;
    }

    public Double getAverageSpeedKmPerHour() {
        return averageSpeedKmPerHour;
    }

    public void setAverageSpeedKmPerHour(Double averageSpeedKmPerHour) {
        this.averageSpeedKmPerHour = averageSpeedKmPerHour;
    }
}