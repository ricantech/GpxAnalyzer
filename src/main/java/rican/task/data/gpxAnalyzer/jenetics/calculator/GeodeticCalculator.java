package rican.task.data.gpxAnalyzer.jenetics.calculator;

import io.jenetics.jpx.WayPoint;

import java.util.List;

public interface GeodeticCalculator {

    double calculateStartNodeEndNodeDistanceInMeters(WayPoint startPoint, WayPoint endPoint);

    double calculateTotalDistance(List<WayPoint> wayPoints);

}