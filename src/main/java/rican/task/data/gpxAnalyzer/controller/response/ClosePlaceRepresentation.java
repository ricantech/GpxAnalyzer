package rican.task.data.gpxAnalyzer.controller.response;

public class ClosePlaceRepresentation extends BaseRepresentation {
    
    private Double distance;
    private String title;
    private String category;
    
    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
