package web.spring.boot.component;

public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable e) {
        super(message, e);
    }
}
