package rican.task.data.gpxAnalyzer.hereapi;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import rican.task.data.gpxAnalyzer.domain.model.ClosePlace;
import rican.task.data.gpxAnalyzer.domain.model.Node;
import rican.task.data.gpxAnalyzer.hereapi.model.Place;
import rican.task.data.gpxAnalyzer.hereapi.model.ResultResponse;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static java.time.LocalDateTime.now;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static rican.task.data.gpxAnalyzer.util.HereApiTestObjectsUtils.createPlace;
import static rican.task.data.gpxAnalyzer.util.HereApiTestObjectsUtils.createResponse;

@Ignore("Needs to be migrated after migration to webclient")
public class HereApiRestClientTest {

    private static final Node NODE = new Node(20.0, 60.0, 19.0, now());

    private HereApiProperties properties = new HereApiProperties();

    private HereApiRestClient client;

    private WebClient webClient;

    @Before
    public void setup() {
        properties.setUrl("http://test");
        webClient = mock(WebClient.class);
        client = new HereApiRestClient(webClient, properties);
    }

    @Test
    public void shouldReturnEmptyListWhenApiIsNotReachable() {
//        when(restTemplate.getForEntity(any(String.class), eq(ResultResponse.class), anyMapOf(String.class, String.class))).thenThrow(ResourceAccessException.class);

        List<ClosePlace> result = client.getClosePlaces(NODE);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListOfClosePlacesWhenRequestFails() {
        ResponseEntity<ResultResponse> notFoundResponse = ResponseEntity.notFound().build();
//        when(restTemplate.getForEntity(any(String.class), eq(ResultResponse.class), anyMapOf(String.class, String.class))).thenReturn(notFoundResponse);

        List<ClosePlace> result = client.getClosePlaces(NODE);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnClosePlacesWhenRequestIsSuccessful() {
        Place firstPlace = createPlace("first place", 1.0, "first category");
        Place secondPlace = createPlace("second place", 2.0, "second category");
        Place thirdPlace = createPlace("third place", 3.0, "third category");
        ResultResponse responseBody = createResponse(of(firstPlace, secondPlace, thirdPlace));

        ResponseEntity<ResultResponse> notFoundResponse = ResponseEntity.ok(responseBody);
//        when(restTemplate.getForEntity(any(String.class), eq(ResultResponse.class), anyMapOf(String.class, String.class))).thenReturn(notFoundResponse);

        List<ClosePlace> result = client.getClosePlaces(NODE);

        assertEquals(3, result.size());
        assertPlace(firstPlace, result.get(0));
        assertPlace(secondPlace, result.get(1));
        assertPlace(thirdPlace, result.get(2));
    }

    private void assertPlace(Place expectedPlace, ClosePlace result) {
        assertEquals(expectedPlace.getTitle(), result.getTitle());
        assertEquals(expectedPlace.getCategoryTitle(), result.getCategory());
        assertEquals(expectedPlace.getDistance(), result.getDistance());
    }
}