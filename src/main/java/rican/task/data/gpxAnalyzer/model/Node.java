package rican.task.data.gpxAnalyzer.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table
public class Node {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "node_seq")
    @SequenceGenerator(name = "node_seq", sequenceName = "node_seq", allocationSize = 100)
    private Long id;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private Double elevation;

    @Column(name = "time_of_record_creation")
    private LocalDateTime timeOfRecordCreation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "path_id", nullable = false)
    private Path path;
    
    @OneToMany(cascade = ALL, orphanRemoval = true, fetch = LAZY, mappedBy = "node")
    private List<ClosePlace> closePlaces = new ArrayList<>();

    Node() {
    }

    public Node(Double latitude, Double longitude, Double elevation, LocalDateTime timeOfRecordCreation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.timeOfRecordCreation = timeOfRecordCreation;
    }

    public Long getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getElevation() {
        return elevation;
    }

    public LocalDateTime getTimeOfRecordCreation() {
        return timeOfRecordCreation;
    }

    public List<ClosePlace> getClosePlaces() {
        return closePlaces;
    }

    public void setClosePlaces(List<ClosePlace> closePlaces) {
        closePlaces.forEach(place -> place.setNode(this));
        this.closePlaces = closePlaces;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}