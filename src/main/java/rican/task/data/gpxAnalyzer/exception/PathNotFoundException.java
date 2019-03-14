package rican.task.data.gpxAnalyzer.exception;

public class PathNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Path with id: %s not found!";

    public PathNotFoundException(Long pathId) {
        super(String.format(ERROR_MESSAGE, pathId));
    }
}