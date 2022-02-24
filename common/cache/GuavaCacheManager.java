
import com.google.common.cache.Cache;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class GuavaCacheManager implements CacheManager {

    private Cache<String, Object> cache;

    public GuavaCacheManager() {
    }

    public void buildCache(Cache<String, Object> cache) {
        this.cache = cache;
    }

    @Override
    public Set<String> getKeys() {
        return cache.asMap().keySet();
    }

    @Override
    public <T> T get(String key) {
        return get(key, null);
    }

    @Override
    public <T> T get(String key, Function<String, T> function) {
        return get(key, function, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, M> T get(String key, Function<M, T> function, M funcParam) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        // 使用 (T) cache.get(key, () -> function.apply(funcParam)) 会将结果存起来
        T obj = (T) cache.getIfPresent(key);
        if (Objects.isNull(obj) && Objects.nonNull(function)) {
            obj = function.apply(funcParam);
        }
        return obj;
    }

    @Override
    public <T> void put(String key, T obj) {
        if (StringUtils.isBlank(key) || Objects.isNull(obj)) {
            return;
        }
        cache.put(key, obj);
    }

    @Override
    public void remove(String key) {
        cache.invalidate(key);
    }
}
