package rican.task.data.gpxAnalyzer.hereapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import rican.task.data.gpxAnalyzer.data.closeplaces.RestClient;
import rican.task.data.gpxAnalyzer.hereapi.model.Place;
import rican.task.data.gpxAnalyzer.hereapi.model.ResultResponse;
import rican.task.data.gpxAnalyzer.model.ClosePlace;
import rican.task.data.gpxAnalyzer.model.Node;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Component
public class HereApiRestClient implements RestClient {

    private static final Logger log = LoggerFactory.getLogger(HereApiRestClient.class);
    private static final String API_VERSION = "v1";
    private static final String CLOSE_PLACE_RESOURCE = "/places/{version}/discover/here?app_id={appId}&app_code={appCode}&at={lat},{lon}&pretty";

    private final RestTemplate restTemplate;
    private final HereApiProperties properties;

    public HereApiRestClient(@Qualifier("hereApiRestTemplate") RestTemplate hereApiRestTemplate,
                             HereApiProperties properties) {
        this.properties = properties;
        this.restTemplate = hereApiRestTemplate;
    }

    @Override
    public List<ClosePlace> getClosePlaces(Node node) {
        Map<String, String> queryParams = authQueryParams();
        queryParams.put("version", API_VERSION);
        queryParams.put("lat", node.getLatitude().toString());
        queryParams.put("lon", node.getLongitude().toString());

        log.info("Preparing to fetch close places for node {lat:{}, lon:{}}", node.getLatitude(), node.getLongitude());
        Optional<ResponseEntity<ResultResponse>> result = fetchClosePlaces(queryParams);
        return result.map(this::convertToClosePlaces).orElseGet(Collections::emptyList);
    }

    private List<ClosePlace> convertToClosePlaces(ResponseEntity<ResultResponse> result) {
        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            return result.getBody().getItems().stream().map(HereApiRestClient::toClosePlace).collect(Collectors.toList());
        }
        return emptyList();
    }

    private Optional<ResponseEntity<ResultResponse>> fetchClosePlaces(Map<String, String> queryParams) {
        try {
            ResponseEntity<ResultResponse> result = restTemplate.getForEntity(CLOSE_PLACE_RESOURCE, ResultResponse.class, queryParams);
            log.info("Close places fetched status:{}", result.getStatusCode());
            return of(result);
        } catch (ResourceAccessException accessException) {
            log.error("HereAPI not accessible", accessException);
            return empty();
        }
    }

    private static ClosePlace toClosePlace(Place place) {
        return new ClosePlace(
                place.getDistance(),
                place.getTitle(),
                place.getCategoryTitle()
        );
    }

    private Map<String, String> authQueryParams() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("appId", properties.getId());
        queryParams.put("appCode", properties.getCode());
        return queryParams;
    }
}