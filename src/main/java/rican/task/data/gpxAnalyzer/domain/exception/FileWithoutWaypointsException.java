package rican.task.data.gpxAnalyzer.domain.exception;

public class FileWithoutWaypointsException extends ProcessingGpxException {

    private static final String ERROR_MESSAGE = "File contains no waypoints - nothing to analyze!";

    public FileWithoutWaypointsException() {
        super(ERROR_MESSAGE);
    }
    
}
