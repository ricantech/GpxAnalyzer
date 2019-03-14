package rican.task.data.gpxAnalyzer.exception;

public class ProcessingGpxException extends RuntimeException {

    public ProcessingGpxException(String message) {
        super(message);
    }

    public ProcessingGpxException(String message, Throwable cause) {
        super(message, cause);
    }
}
