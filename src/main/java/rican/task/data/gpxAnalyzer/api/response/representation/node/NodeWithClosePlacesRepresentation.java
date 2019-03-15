package rican.task.data.gpxAnalyzer.api.response.representation.node;

import rican.task.data.gpxAnalyzer.api.response.representation.closeplace.ClosePlaceRepresentation;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

public class NodeWithClosePlacesRepresentation extends NodeRepresentation {

    private List<ClosePlaceRepresentation> closePlaces;

    public NodeWithClosePlacesRepresentation(NodeRepresentation nodeRepresentation) {
        copyProperties(nodeRepresentation, this);
    }

    public List<ClosePlaceRepresentation> getClosePlaces() {
        return closePlaces;
    }

    public void setClosePlaces(List<ClosePlaceRepresentation> closePlaces) {
        this.closePlaces = closePlaces;
    }
}