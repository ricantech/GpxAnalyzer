package rican.task.data.gpxAnalyzer.api.response;

import org.springframework.stereotype.Component;
import rican.task.data.gpxAnalyzer.api.controller.PathController;
import rican.task.data.gpxAnalyzer.api.response.representation.closeplace.ClosePlaceRepresentation;
import rican.task.data.gpxAnalyzer.api.response.representation.node.NodeRepresentation;
import rican.task.data.gpxAnalyzer.api.response.representation.node.NodeWithClosePlacesRepresentation;
import rican.task.data.gpxAnalyzer.api.response.representation.path.PathRepresentation;
import rican.task.data.gpxAnalyzer.api.response.representation.path.PathWithDetailRepresentation;
import rican.task.data.gpxAnalyzer.domain.model.ClosePlace;
import rican.task.data.gpxAnalyzer.domain.model.Node;
import rican.task.data.gpxAnalyzer.domain.model.Path;

import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class RepresentationConverter {

    public PathWithDetailRepresentation convertWithDetail(Path path) {
        PathWithDetailRepresentation pathWithDetailRepresentation = new PathWithDetailRepresentation(convert(path));
        pathWithDetailRepresentation.setStartNode(convert(path.getStartNode()));
        pathWithDetailRepresentation.setLastNode(convertWithClosePlaces(path.getLastNode()));
        pathWithDetailRepresentation.setStartNodeEndNodeDistanceInMeters(path.getStartNodeEndNodeDistance());
        pathWithDetailRepresentation.setTotalDistanceInMeters(path.getTotalDistance());
        pathWithDetailRepresentation.setAverageSpeedKmPerHour(path.getAverageSpeedKmPerHour());
        pathWithDetailRepresentation.setPathDetails(path.getNodes().stream().map(this::convert).collect(Collectors.toList()));
        pathWithDetailRepresentation.add(linkTo(PathController.class).slash(path.getId()).withSelfRel());
        return pathWithDetailRepresentation;
    }

    public PathRepresentation convertWithDetailLink(Path path) {
        PathRepresentation pathRepresentation = convert(path);
        pathRepresentation.add(linkTo(PathController.class).slash(path.getId()).withRel("pathDetails"));
        return pathRepresentation;
    }

    private PathRepresentation convert(Path path) {
        PathRepresentation pathRepresentation = new PathRepresentation();

        pathRepresentation.setItemId(path.getId());
        pathRepresentation.setCreated(path.getCreated());

        return pathRepresentation;
    }

    private ClosePlaceRepresentation convert(ClosePlace closePlace) {
        ClosePlaceRepresentation closePlaceRepresentation = new ClosePlaceRepresentation();
        closePlaceRepresentation.setItemId(closePlace.getId());
        closePlaceRepresentation.setTitle(closePlace.getTitle());
        closePlaceRepresentation.setCategory(closePlace.getCategory());
        closePlaceRepresentation.setDistance(closePlace.getDistance());
        return closePlaceRepresentation;
    }

    private NodeWithClosePlacesRepresentation convertWithClosePlaces(Node node) {
        NodeWithClosePlacesRepresentation nodeWithClosePlacesRepresentation = new NodeWithClosePlacesRepresentation(convert(node));
        nodeWithClosePlacesRepresentation.setClosePlaces(node.getClosePlaces().stream().map(this::convert).collect(Collectors.toList()));
        return nodeWithClosePlacesRepresentation;

    }

    private NodeRepresentation convert(Node node) {
        NodeRepresentation nodeRepresentation = new NodeRepresentation();
        nodeRepresentation.setItemId(node.getId());
        nodeRepresentation.setLatitude(node.getLatitude());
        nodeRepresentation.setLongitude(node.getLongitude());
        nodeRepresentation.setElevation(node.getElevation());
        nodeRepresentation.setTimeOfRecordCreation(node.getTimeOfRecordCreation());
        return nodeRepresentation;
    }
}