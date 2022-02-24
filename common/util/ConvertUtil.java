
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ConvertUtil {

    /**
     * DBList 转 VOList
     * VO需要（DB, VOTypeEnum）的构造函数
     *
     * @param list
     * @param toClass
     * @param <T>
     * @param <D>
     * @return
     */
    public static <T, D> List<D> convert(List<T> list, Class<D> toClass) {
        return list.parallelStream().map(t -> {
            try {
                Constructor<D> constructor = toClass.getConstructor(t.getClass());
                return constructor.newInstance(t);
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                log.error(String.format("DBList 转 VOList, VO%s需要（DB）的构造函数", toClass), e);
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}
