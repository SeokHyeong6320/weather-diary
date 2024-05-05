package zerobase.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoDiaryException extends ServiceException {


    public NoDiaryException(String message) {
        super(message);
    }
}
