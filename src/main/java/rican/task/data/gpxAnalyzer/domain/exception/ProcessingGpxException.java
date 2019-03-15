package rican.task.data.gpxAnalyzer.domain.exception;

public class ProcessingGpxException extends RuntimeException {

    public ProcessingGpxException(String message) {
        super(message);
    }

    public ProcessingGpxException(String message, Throwable cause) {
        super(message, cause);
    }
}
