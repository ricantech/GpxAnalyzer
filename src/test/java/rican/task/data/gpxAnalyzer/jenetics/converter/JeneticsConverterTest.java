package rican.task.data.gpxAnalyzer.jenetics.converter;

import io.jenetics.jpx.WayPoint;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.web.multipart.MultipartFile;
import rican.task.data.gpxAnalyzer.exception.FileWithoutWaypointsException;
import rican.task.data.gpxAnalyzer.exception.InputStreamNotReadableException;
import rican.task.data.gpxAnalyzer.jenetics.calculator.GeodeticCalculator;
import rican.task.data.gpxAnalyzer.model.Node;
import rican.task.data.gpxAnalyzer.model.Path;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.of;
import static org.junit.Assert.assertEquals;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rican.task.data.gpxAnalyzer.util.TestFileUtils.*;

public class JeneticsConverterTest {

    @Rule
    public ExpectedException expectedException = none();

    private static final Double START_TO_END_DISTANCE = 10.0;
    private static final Double TOTAL_DISTANCE = 100.0;
    private static final Double AVERAGE_SPEED = 6.0;

    private final GeodeticCalculator geodeticCalculator = mock(GeodeticCalculator.class);

    private final JeneticsConverter converter = new JeneticsConverter(geodeticCalculator);

    @Before
    public void setup() {
        when(geodeticCalculator.calculateStartNodeEndNodeDistanceInMeters(any(WayPoint.class), any(WayPoint.class))).thenReturn(START_TO_END_DISTANCE);
        when(geodeticCalculator.calculateTotalDistance(anyListOf(WayPoint.class))).thenReturn(TOTAL_DISTANCE);
    }

    @Test
    public void shouldConvertPgxFileToDomainObject() {
        MultipartFile multipartFile = createOkFile();

        Path result = converter.convert(multipartFile);


        assertEquals(TOTAL_DISTANCE, result.getTotalDistance());
        assertEquals(START_TO_END_DISTANCE, result.getStartNodeEndNodeDistance());
        assertEquals(AVERAGE_SPEED, result.getAverageSpeed());
        assertEquals(2, result.getNodes().size());
        assertNode(result.getNodes().get(0), 21.0, 60.0, 19.0, of(2015, 1, 20, 13, 30));
        assertNode(result.getNodes().get(1), 21.0, 60.009655, 19.0, of(2015, 1, 20, 13, 31));
    }

    @Test
    public void shouldThrowFileWithoutEnpointExceptionIfThereIsNoWaypoint() {
        expectedException.expect(FileWithoutWaypointsException.class);
        MultipartFile multipartFile = createFileWithoutWaypoints();

        converter.convert(multipartFile);
    }

    @Test
    public void shouldThrowInputStreamNotReadableExceptionIfStreamIsCorrupted() {
        expectedException.expect(InputStreamNotReadableException.class);
        MultipartFile multipartFile = createCorruptedFile();

        converter.convert(multipartFile);
    }

    private void assertNode(Node node, Double lat, Double lon, Double ele, LocalDateTime nodeCreated) {
        assertEquals(lat, node.getLatitude());
        assertEquals(lon, node.getLongitude());
        assertEquals(ele, node.getElevation());
        assertEquals(nodeCreated, node.getTimeOfRecordCreation());
    }
}