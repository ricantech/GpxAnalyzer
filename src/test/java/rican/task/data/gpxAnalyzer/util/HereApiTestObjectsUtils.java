package rican.task.data.gpxAnalyzer.util;

import rican.task.data.gpxAnalyzer.hereapi.model.Category;
import rican.task.data.gpxAnalyzer.hereapi.model.Place;
import rican.task.data.gpxAnalyzer.hereapi.model.Places;
import rican.task.data.gpxAnalyzer.hereapi.model.ResultResponse;

import java.util.List;

public class HereApiTestObjectsUtils {
    
    private HereApiTestObjectsUtils() {

    }

    public static Place createPlace(String title, Double distance, String categoryTitle) {
        Place place = new Place();
        place.setTitle(title);
        place.setDistance(distance);

        Category category = new Category();
        category.setTitle(categoryTitle);
        place.setCategory(category);
        return place;
    }

    public static ResultResponse createResponse(List<Place> foundPlaces) {
        ResultResponse resultResponse = new ResultResponse();
        Places places = new Places();
        places.setItems(foundPlaces);
        resultResponse.setResults(places);
        return resultResponse;
    }
}