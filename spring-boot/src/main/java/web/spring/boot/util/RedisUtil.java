package web.spring.boot.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private static RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init() {
        redisUtil = this;
    }

    public static ValueOperations<String, Object> opsForValue() {
        return redisUtil.redisTemplate.opsForValue();
    }

    public static ListOperations<String, Object> opsForList() {
        return redisUtil.redisTemplate.opsForList();
    }

    public static SetOperations<String, Object> opsForSet() {
        return redisUtil.redisTemplate.opsForSet();
    }

    public static HashOperations<String, String, Object> opsOfHash() {
        return redisUtil.redisTemplate.opsForHash();
    }

    /**
     * 删除键
     * @param keys 键
     */
    public static void delete(String... keys) {

        redisUtil.redisTemplate.delete(Arrays.asList(keys));
    }

    /**
     * 是否存在键
     * @param key 键
     * @return 如果存在返回 true
     */
    public static boolean hasKey(String key) {
        return redisUtil.redisTemplate.hasKey(key);
    }

    /**
     * 设置键超时时间
     * @param key 键
     * @param timeout 超时时间。单位：秒
     */
    public static void expire(String key, long timeout) {
        redisUtil.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置键超时时间
     * @param key 键
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     */
    public static void expire(String key, long timeout, TimeUnit timeUnit) {
        redisUtil.redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 设置键到期时间
     * @param key 键
     * @param date 日期时间
     */
    public static void expireAt(String key, Date date) {
        redisUtil.redisTemplate.expireAt(key, date);
    }

    /**
     * 获取键过期时间
     * @param key 键
     * @return 过期时间。单位：秒
     */
    public static long getExpire(String key) {
        return redisUtil.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public static long getExpire(String key, TimeUnit timeUnit) {
        return redisUtil.redisTemplate.getExpire(key, timeUnit);
    }
}
