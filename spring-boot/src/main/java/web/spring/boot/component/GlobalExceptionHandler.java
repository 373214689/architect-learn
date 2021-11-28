package web.spring.boot.component;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import web.spring.boot.entity.Message;

@Component
@Aspect
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Message<BaseException> processMethodArgumentException(MethodArgumentNotValidException e) {
        return Message.create(Message.BAD_REQUEST, new BaseException(e.getMessage(), e), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Message<BaseException> processOtherException(Exception e) {
        return Message.create(Message.BAD_REQUEST, new BaseException(e.getMessage(), e), e.getMessage());
    }

}
