package rican.task.data.gpxAnalyzer.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rican.task.data.gpxAnalyzer.api.response.representation.error.ErrorResponse;
import rican.task.data.gpxAnalyzer.domain.exception.PathNotFoundException;
import rican.task.data.gpxAnalyzer.domain.exception.ProcessingGpxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        List<String> messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        return new ResponseEntity<>(new ErrorResponse<>(messages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PathNotFoundException.class)
    public ResponseEntity<?> handlePathNotFoundException(PathNotFoundException ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorResponse<>(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProcessingGpxException.class)
    public ResponseEntity<?> handleProcessingGpxException(ProcessingGpxException ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorResponse<>(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}