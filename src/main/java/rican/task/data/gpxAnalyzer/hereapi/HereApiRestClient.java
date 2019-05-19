package rican.task.data.gpxAnalyzer.hereapi;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import rican.task.data.gpxAnalyzer.domain.model.ClosePlace;
import rican.task.data.gpxAnalyzer.domain.model.Node;
import rican.task.data.gpxAnalyzer.domain.service.closeplaces.RestClient;
import rican.task.data.gpxAnalyzer.hereapi.model.Place;
import rican.task.data.gpxAnalyzer.hereapi.model.ResultResponse;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Component
public class HereApiRestClient implements RestClient {

    private static final Logger log = LoggerFactory.getLogger(HereApiRestClient.class);
    private static final String API_VERSION = "v1";
    private static final String CLOSE_PLACE_RESOURCE = "/places/{version}/discover/here?app_id={appId}&app_code={appCode}&at={lat},{lon}&pretty";

    private final WebClient hereApiClient;
    private final HereApiProperties properties;

    public HereApiRestClient(WebClient hereApiClient,
                             HereApiProperties properties) {
        this.properties = properties;
        this.hereApiClient = hereApiClient;
    }

    @Override
    public List<ClosePlace> getClosePlaces(Node node) {
        Map<String, String> queryParams = authQueryParams();
        queryParams.put("version", API_VERSION);
        queryParams.put("lat", node.getLatitude().toString());
        queryParams.put("lon", node.getLongitude().toString());

        log.info("Preparing to fetch close places for node {lat:{}, lon:{}}", node.getLatitude(), node.getLongitude());
        Optional<Mono<ResultResponse>> result = fetchClosePlaces(queryParams);
        return result.map(this::convertToClosePlaces).orElseGet(Collections::emptyList);
    }

    private List<ClosePlace> convertToClosePlaces(Mono<ResultResponse> result) {
        ResultResponse hereApiResponse = result.block();
        if (hereApiResponse == null) {
            log.warn("No result retrieved for close places. Returning empty list!");
            return ImmutableList.of();
        }
        return hereApiResponse.getItems().stream().map(HereApiRestClient::toClosePlace).collect(Collectors.toList());
    }

    private Optional<Mono<ResultResponse>> fetchClosePlaces(Map<String, String> queryParams) {
        try {
            Mono<ResultResponse> result = hereApiClient.get().uri(CLOSE_PLACE_RESOURCE, queryParams)
                    .header("Accept", APPLICATION_JSON_UTF8_VALUE)
                    .retrieve()
                    .onStatus(HttpStatus::isError, this::logAndReturnEmptyResult)
                    .bodyToMono(ResultResponse.class);
            return of(result);
        } catch (ResourceAccessException accessException) {
            log.error("HereAPI not accessible", accessException);
            return empty();
        }
    }

    private Mono logAndReturnEmptyResult(ClientResponse clientResponse) {
        log.error("Error returned from hereAPI: {}", clientResponse);
        return Mono.empty();
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