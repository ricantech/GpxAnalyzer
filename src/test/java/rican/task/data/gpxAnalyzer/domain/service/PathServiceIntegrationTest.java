package rican.task.data.gpxAnalyzer.domain.service;

import com.google.common.collect.Lists;
import com.squareup.okhttp.mockwebserver.MockResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rican.task.data.gpxAnalyzer.domain.model.Path;
import rican.task.data.gpxAnalyzer.domain.repository.PathRepository;
import rican.task.data.gpxAnalyzer.hereapi.server.HereApiTestWebServer;
import rican.task.data.gpxAnalyzer.hereapi.server.HereApiTestWebServerConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static rican.task.data.gpxAnalyzer.util.TestFileUtils.createOkFile;
import static rican.task.data.gpxAnalyzer.util.TestFileUtils.createSuccessHereApiResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integration-test.properties")
@Import(HereApiTestWebServerConfiguration.class)
public class PathServiceIntegrationTest {

    private static final int EXPECTED_NUMBER_OF_NODES = 2;
    private static final int EXPECTED_NUMBER_OF_CLOSE_PLACES = 20;

    @Autowired
    private PathService pathService;

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private HereApiTestWebServer hereApiTestWebServer;

    @Test
    @Transactional
    public void shouldProcessMultiPartFileAndStoreIntoDb() {
        MultipartFile okFile = createOkFile();
        mockSuccessHereApiResponse();

        Path result = pathService.process(okFile);

        assertPath(result, EXPECTED_NUMBER_OF_CLOSE_PLACES);
        List<Path> paths = Lists.newArrayList(pathRepository.findAll());
        assertEquals(1, paths.size());
        assertPath(paths.get(0), EXPECTED_NUMBER_OF_CLOSE_PLACES);
    }

    @Test
    @Transactional
    public void shouldReturnEmtpyListWhenErrorIsReceivedFromHereApi() {
        MultipartFile okFile = createOkFile();
        mock4xxHereApiResponse();

        Path result = pathService.process(okFile);

        assertPath(result, 0);
    }

    private void mock4xxHereApiResponse() {
        hereApiTestWebServer.mockResponse(
                new MockResponse()
                        .setResponseCode(BAD_REQUEST.value())
        );
    }

    private void mockSuccessHereApiResponse() {
        hereApiTestWebServer.mockResponse(
                new MockResponse()
                        .setHeader("Content-Type", APPLICATION_JSON_UTF8_VALUE)
                        .setResponseCode(OK.value())
                        .setBody(createSuccessHereApiResponse())
        );
    }

    private void assertPath(Path path, int numberOfClosePlaces) {
        assertEquals(EXPECTED_NUMBER_OF_NODES, path.getNodes().size());
        assertEquals(numberOfClosePlaces, path.getLastNode().getClosePlaces().size());
        assertEquals(path.getStartNode(), path.getNodes().get(0));
        assertEquals(path.getLastNode(), path.getNodes().get(1));
    }
}