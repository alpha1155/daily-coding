package Spring;

import java.util.Collections;

public class TokenBucketRateLimiter {
    private StringRedisTemplate redisTemplate;

    private static final String SCRIPT = "
    local key = KEYS[1]
    local window = tonumber(ARGV[1])
    local threshold = tonumber(ARGV[2])
    local now = tonumber(ARGV[3])
    local start = now - window * 1000
    // 作用：删除 ZSET 中 score 在 [min, max) 范围内的成员
    // 参数：

    // key：限流键（如 rate:limit:user123）
    // -inf：负无穷大，代表“所有小于 max 的 score”
    // start：窗口左边界时间戳
    redis.call('ZREMRANGEBYSCORE', key, '-inf',start)

    local count = redis.call('ZCARD', key)
    if count < threshold then
        redis.call('ZADD', key ,now,now)
        redis.call('EXPIRE', key, window +1)
        retur 1
    else
        return 0
    end
    ";

    private final DefaultRedisScript<Long> script = new DefaultRedisScript<>(SCRIPT, Long.class)



    public boolean tryAcquire(String key) {
        long now = System.currentTimeMillis();
        Long result = redisTemplate.execute(script, Collections.singletoList("rale:limit:" + key),
                "60", "100", String.valueOf(now));
        return result != null && result == 1;
    }
}
