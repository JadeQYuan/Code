
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {

    private static final ObjectMapper objectMapper;

    static {
        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
        builder
                .simpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS") // 格式化日期
                .timeZone("GMT+8") // 设置时区
                .failOnUnknownProperties(false) // 位置属性不抛出异常
                .serializationInclusion(JsonInclude.Include.NON_NULL) // 忽略null属性
        ;
        objectMapper = builder.build();
    }

    public static String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw SysResponseEnum.SYS_EXCEPTION.newException(e);
        }
    }

    public static <T> T read(String text, Class<T> valueType) {
        try {
            return objectMapper.readValue(text, valueType);
        } catch (IOException e) {
            throw SysResponseEnum.SYS_EXCEPTION.newException(e);
        }
    }

    public static <T> T read(String text, Class<?> collectionClass, Class<?>... elementClasses) {
        try {
            JavaType valueType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return objectMapper.readValue(text, valueType);
        } catch (IOException e) {
            throw SysResponseEnum.SYS_EXCEPTION.newException(e);
        }
    }

    public static <K, V> Map<K, V> readMap(String text, Class<K> keyClass, Class<V> valueClass) {
        try {
            JavaType valueType = objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
            return objectMapper.readValue(text, valueType);
        } catch (IOException e) {
            throw SysResponseEnum.SYS_EXCEPTION.newException(e);
        }
    }
}
