package zerobase.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DiaryException extends ServiceException {

    private String message;


    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public DiaryException() {
        super();
    }

    public DiaryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiaryException(Throwable cause) {
        super(cause);
    }

    public DiaryException(String message) {
        super(message);
        this.message = message;
    }
}
