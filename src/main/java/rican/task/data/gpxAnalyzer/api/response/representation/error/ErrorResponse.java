package rican.task.data.gpxAnalyzer.api.response.representation.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.Collections.singletonList;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class ErrorResponse<T> {
    private List<T> errors;

    public ErrorResponse(List<T> errors) {
        this.errors = errors;
    }

    public ErrorResponse(T error) {
        this(singletonList(error));
    }

    public List<T> getErrors() {
        return errors;
    }
}