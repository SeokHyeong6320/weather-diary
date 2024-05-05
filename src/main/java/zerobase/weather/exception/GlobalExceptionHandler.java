package zerobase.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoDiaryException.class)
    public ErrorResult diaryExceptionHandler(NoDiaryException e) {
        return new ErrorResult("HttpStatus.BAD_REQUEST", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult globalExceptionHandler(Exception e) {
        return new ErrorResult("HttpStatus.INTERNAL_SERVER_ERROR", e.getMessage());
    }


}
