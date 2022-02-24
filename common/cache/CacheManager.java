
import java.util.Set;
import java.util.function.Function;

public interface CacheManager {

    /**
     * 查询所有缓存键
     *
     * @return
     */
    Set<String> getKeys();

    /**
     * 查询缓存
     *
     * @param key 缓存键 不可为空
     **/
    <T> T get(String key);

    /**
     * 查询缓存
     *
     * @param key      缓存键 不可为空
     * @param function 如没有缓存，调用该callable函数返回对象 可为空
     **/
    <T> T get(String key, Function<String, T> function);

    /**
     * 查询缓存
     *
     * @param key       缓存键 不可为空
     * @param function  如没有缓存，调用该callable函数返回对象 可为空
     * @param funcParam function函数的调用参数
     **/
    <T, M> T get(String key, Function<M, T> function, M funcParam);

    /**
     * 设置缓存键值
     *
     * @param key 缓存键 不可为空
     * @param obj 缓存值 不可为空
     **/
    <T> void put(String key, T obj);

    /**
     * 移除缓存
     *
     * @param key 缓存键 不可为空
     **/
    void remove(String key);
}
