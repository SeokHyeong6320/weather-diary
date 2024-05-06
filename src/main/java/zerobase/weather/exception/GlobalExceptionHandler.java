package zerobase.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResult> diaryExceptionHandler(ServiceException e) {
        ErrorResult errorResult = new ErrorResult("HttpStatus.BAD_REQUEST", e.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.resolve(e.getStatusCode()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> globalExceptionHandler(Exception e) {
        ErrorResult errorResult = new ErrorResult("HttpStatus.INTERNAL_SERVER_ERROR", e.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
