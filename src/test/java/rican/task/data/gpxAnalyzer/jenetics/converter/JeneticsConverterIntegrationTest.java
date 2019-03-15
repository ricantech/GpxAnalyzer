package rican.task.data.gpxAnalyzer.jenetics.converter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import rican.task.data.gpxAnalyzer.exception.FileWithoutWaypointsException;
import rican.task.data.gpxAnalyzer.exception.InputStreamNotReadableException;
import rican.task.data.gpxAnalyzer.model.Node;
import rican.task.data.gpxAnalyzer.model.Path;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;


//TODO can be removed
@SpringBootTest
@RunWith(SpringRunner.class)
public class JeneticsConverterIntegrationTest {

    @Rule
    public ExpectedException expectedException = none();

    private static final String OK_FILE = "gpx/test_data_ok.gpx";
    private static final String STREAM_CORRUPTED_FILE = "gpx/test_data_corrupted.gpx";
    private static final String EMPTY_FILE = "gpx/test_data_without_nodes.gpx";

    @Autowired
    private JeneticsConverter converter;

    @Test
    public void shouldConvertPgxFileToDomainObject() {
        MultipartFile multipartFile = createMultipartFile(OK_FILE);

        Path result = converter.convert(multipartFile);

        assertThat(result.getTotalDistance(), is(1003.8342236200876));
        assertThat(result.getStartNodeEndNodeDistance(), is(1003.8342236200876));
        assertThat(result.getAverageSpeedKmPerHour(), is(60.23005341720526));
        assertThat(result.getNodes().size(), is(2));
        assertNode(result.getNodes().get(0), 21, 60, 19.0, of(2015, 1, 20, 13, 30));
        assertNode(result.getNodes().get(1), 21, 60.009655, 19.0, of(2015, 1, 20, 13, 31));
    }

    @Test
    public void shouldThrowFileWithoutEnpointExceptionIfThereIsNoWaypoint() {
        expectedException.expect(FileWithoutWaypointsException.class);
        MultipartFile multipartFile = createMultipartFile(EMPTY_FILE);

        converter.convert(multipartFile);
    }

    @Test
    public void shouldThrowInputStreamNotReadableExceptionIfStreamIsCorrupted() {
        expectedException.expect(InputStreamNotReadableException.class);
        MultipartFile multipartFile = createMultipartFile(STREAM_CORRUPTED_FILE);

        converter.convert(multipartFile);
    }

    private void assertNode(Node node, double lat, double lon, double ele, LocalDateTime nodeCreated) {
        assertThat(node.getLatitude(), is(lat));
        assertThat(node.getLongitude(), is(lon));
        assertThat(node.getElevation(), is(ele));
        assertThat(node.getTimeOfRecordCreation(), is(nodeCreated));
    }

    private MockMultipartFile createMultipartFile(String name) {
        try {
            return new MockMultipartFile(name, loadTestingFile(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream loadTestingFile(String fileName) {
        return this.getClass().getClassLoader().getResourceAsStream(fileName);
    }
}