package rican.task.data.gpxAnalyzer.jenetics.calculator;

import io.jenetics.jpx.WayPoint;
import io.jenetics.jpx.geom.Geoid;

import java.util.List;

public abstract class AbstractGeodeticCalculator implements GeodeticCalculator {

    private final Geoid geodeticFunction;

    public AbstractGeodeticCalculator(Geoid geodeticFunction) {
        this.geodeticFunction = geodeticFunction;
    }

    @Override
    public double calculateStartNodeEndNodeDistanceInMeters(WayPoint startPoint, WayPoint endPoint) {
        return geodeticFunction.distance(startPoint, endPoint).doubleValue();
    }

    @Override
    public double calculateTotalDistance(List<WayPoint> wayPoints) {
        return wayPoints.stream().collect(geodeticFunction.toPathLength()).doubleValue();
    }
}
