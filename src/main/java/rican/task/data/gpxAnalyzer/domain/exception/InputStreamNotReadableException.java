package rican.task.data.gpxAnalyzer.domain.exception;

public class InputStreamNotReadableException extends ProcessingGpxException {

    private static final String ERROR_MESSAGE = "File is not readable!";

    public InputStreamNotReadableException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }

}