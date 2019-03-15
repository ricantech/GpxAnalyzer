package rican.task.data.gpxAnalyzer.domain.service.closeplaces;

import rican.task.data.gpxAnalyzer.domain.model.ClosePlace;
import rican.task.data.gpxAnalyzer.domain.model.Node;

import java.util.List;

public interface RestClient {
    List<ClosePlace> getClosePlaces(Node node);
}