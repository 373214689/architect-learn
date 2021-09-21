package web.spring.boot.entity;


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

    public static <T> Message<T> create(int code, String token, T message) {
        return new Message<T> (code, token, message);
    }

    public static <T> Message<T> create(int code, T message) {
        return new Message<T> (code, null, message);
    }


    private int code;

    private String token;

    private T message;

    public Message(int code, String token, T message) {
        this.code = code;
        this.token = token;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }

    public T getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
