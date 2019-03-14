package rican.task.data.gpxAnalyzer;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import rican.task.data.gpxAnalyzer.hereapi.HereApiProperties;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static rican.task.data.gpxAnalyzer.util.TestFileUtils.createSuccessHereApiResponse;

@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class AbstractIntegrationTest {

    private MockRestServiceServer mockServer;

    @Autowired
    private HereApiProperties hereApiProperties;

    @Autowired
    @Qualifier("hereApiRestTemplate")
    private RestTemplate hereApiRestTemplate;

    @Before
    public void setup() {
        mockServer = MockRestServiceServer.createServer(hereApiRestTemplate);
        mockServer.expect(requestTo(hereApiProperties.getUrl() + "places/v1/discover/here?app_id=test&app_code=test&at=21.0,60.009655&pretty"))
                .andRespond(withSuccess(createSuccessHereApiResponse(), APPLICATION_JSON));
    }
}