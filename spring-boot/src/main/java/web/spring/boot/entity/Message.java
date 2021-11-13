package web.spring.boot.entity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        return new Message<>(OK, null, message);
    }

    public static <T> Message<T> create(int code, T data, String message) {
        return new Message<>(OK, data, message);
    }

    private int code;
    private T data;
    private String message;

    public Message(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
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
