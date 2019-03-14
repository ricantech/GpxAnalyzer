package rican.task.data.gpxAnalyzer.data.closeplaces;

import rican.task.data.gpxAnalyzer.model.ClosePlace;
import rican.task.data.gpxAnalyzer.model.Node;

import java.util.List;

public interface RestClient {
    List<ClosePlace> getClosePlaces(Node node);
}