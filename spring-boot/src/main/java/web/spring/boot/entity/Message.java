package web.spring.boot.entity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class Message <T> {
    /** 成功 */
    public static final int OK = 200;
    /** 宫户端错误请求 */
    public static final int BAD_REQUEST = 400;
    /** 客户端未检验身份 */
    public static final int UNAUTHORIZED = 401;

    public static final int FORBIDDEN = 403;

    public static final int NOT_FOUND = 404;

    public static final int METHOD_NOT_ALLOW = 405;

    public static final int NOT_ACCEPTABLE = 406;

    public static final int REQUEST_TIMEOUT = 408;

    public static final int SERVER_ERROR = 500;

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> Message<T> success(T data) {
        return new Message<>(OK, data, null);
    }

    public static <T> Message<T> error(String message) {
        return new Message<>(BAD_REQUEST, null, message);
    }

    public static <T> Message<T> forbidden(String message) {
        return new Message<>(FORBIDDEN, null, message);
    }

    public static <T> Message<T> create(int code, T data, String message) {
        return new Message<>(code, data, message);
    }

    private int code;
    private T data;
    private String message;

    public Message(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
