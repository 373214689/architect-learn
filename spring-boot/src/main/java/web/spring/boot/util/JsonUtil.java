package web.spring.boot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String writeValueAsString(Object o) {

        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
