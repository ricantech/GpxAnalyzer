package rican.task.data.gpxAnalyzer.jenetics.calculator;

import com.google.common.collect.ImmutableList;
import io.jenetics.jpx.WayPoint;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WGS84CalculatorTest {

    private static final WayPoint START_WAYPOINT = WayPoint.of(21, 60);
    private static final WayPoint FINAL_WAYPOINT = WayPoint.of(21, 61);
    private static final double EXPECTED_DISTANCE = 103970.23173748628;


    private WGS84Calculator calculator = new WGS84Calculator();

    @Test
    public void shouldCalculateCorrectDistanceBetweenTwoWaypoints() {
        double result = calculator.calculateStartNodeEndNodeDistanceInMeters(START_WAYPOINT, FINAL_WAYPOINT);

        assertEquals(EXPECTED_DISTANCE, result, 0);
    }

    @Test
    public void shouldCalculateCorrectDistanceFromListOfPoints() {
        double result = calculator.calculateTotalDistance(ImmutableList.of(START_WAYPOINT, FINAL_WAYPOINT));

        assertEquals(EXPECTED_DISTANCE, result, 0);
    }
}