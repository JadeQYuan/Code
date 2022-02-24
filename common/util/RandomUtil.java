
import java.util.Random;
import java.util.UUID;

/**
 * 随机数工具类
 */
public class RandomUtil {

    private RandomUtil() {
    }

    private static Random random = new Random(System.currentTimeMillis());

    /**
     * 获取UUID
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成指定长度的随机字符串
     *
     * @param len
     * @return
     */
    public static String generateByLength(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; ++i) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 根据最大值生成随机字符串
     *
     * @param bound
     * @return
     */
    public static String nextInt(int bound) {
        return String.valueOf(random.nextInt(bound));
    }
}
