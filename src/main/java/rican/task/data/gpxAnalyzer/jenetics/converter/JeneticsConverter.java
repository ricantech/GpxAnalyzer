package rican.task.data.gpxAnalyzer.jenetics.converter;

import io.jenetics.jpx.Length;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;
import io.jenetics.jpx.WayPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import rican.task.data.gpxAnalyzer.service.converter.DomainConverter;
import rican.task.data.gpxAnalyzer.exception.FileWithoutWaypointsException;
import rican.task.data.gpxAnalyzer.exception.InputStreamNotReadableException;
import rican.task.data.gpxAnalyzer.jenetics.calculator.GeodeticCalculator;
import rican.task.data.gpxAnalyzer.model.Node;
import rican.task.data.gpxAnalyzer.model.Path;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.LinkedList;

import static io.jenetics.jpx.GPX.read;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.stream.Collectors.toCollection;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class JeneticsConverter implements DomainConverter {

    private static final Logger log = LoggerFactory.getLogger(JeneticsConverter.class);

    private static final double MS_TO_KMH_CONSTANT = 3.6;

    private final GeodeticCalculator geodeticCalculator;

    public JeneticsConverter(GeodeticCalculator geodeticCalculator) {
        this.geodeticCalculator = geodeticCalculator;
    }

    @Override
    public Path convert(MultipartFile file) {
        Path result = new Path();

        LinkedList<WayPoint> wayPoints = loadWaypoints(file);
        if (isEmpty(wayPoints)) {
            log.warn("File \"{}\" contains no waypoints, nothing to process - aborting!", file.getOriginalFilename());
            throw new FileWithoutWaypointsException();
        }

        LinkedList<Node> convertedNodes = wayPoints.parallelStream()
                .map(JeneticsConverter::asNode)
                .collect(toCollection(LinkedList::new));

        Node firstNode = convertedNodes.getFirst();
        Node lastNode = convertedNodes.getLast();

        result.setStartNode(firstNode);
        result.setNodes(convertedNodes);
        result.setLastNode(lastNode);

        double startEndDistanceInMeters = geodeticCalculator.calculateStartNodeEndNodeDistanceInMeters(wayPoints.getFirst(), wayPoints.getLast());
        double totalDistanceInMeters = geodeticCalculator.calculateTotalDistance(wayPoints);
        long totalDurationInSeconds = SECONDS.between(firstNode.getTimeOfRecordCreation(), lastNode.getTimeOfRecordCreation());
        double averageSpeedKmPerHours = (totalDistanceInMeters / totalDurationInSeconds) * MS_TO_KMH_CONSTANT;

        result.setTotalDistance(totalDistanceInMeters);
        result.setStartNodeEndNodeDistance(startEndDistanceInMeters);
        result.setAverageSpeedKmPerHour(averageSpeedKmPerHours);
        return result;
    }

    private static Node asNode(WayPoint wayPoint) {
        return new Node(
                wayPoint.getLatitude().doubleValue(),
                wayPoint.getLongitude().doubleValue(),
                wayPoint.getElevation().map(Length::doubleValue).orElse(null),
                wayPoint.getTime().map(ZonedDateTime::toLocalDateTime).orElse(null)
        );
    }

    private LinkedList<WayPoint> loadWaypoints(MultipartFile file) {
        try {
            return read(file.getInputStream())
                    .tracks()
                    .flatMap(Track::segments)
                    .flatMap(TrackSegment::points)
                    .collect(toCollection(LinkedList::new));
        } catch (IOException e) {
            log.error("Unable to process file \"{}\"", file.getOriginalFilename(), e);
            throw new InputStreamNotReadableException(e);
        }
    }
}