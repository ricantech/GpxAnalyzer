package rican.task.data.gpxAnalyzer.domain.model;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "close_place")
public class ClosePlace {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "close_place_seq")
    @SequenceGenerator(name = "close_place_seq", sequenceName = "close_place_seq")
    private Long id;

    @Column
    private Double distance;

    @Column(length = 150)
    private String title;

    @Column(length = 50)
    private String category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private Node node;

    ClosePlace() {
    }

    public ClosePlace(Double distance, String title, String category) {
        this.distance = distance;
        this.title = title;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public Double getDistance() {
        return distance;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}