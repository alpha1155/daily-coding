package Spring;

import org.springframework.data.redis.core.StringRedisTemplate;

@Component
public class SlidingWindowRateLimiter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // Lua 脚本（原子执行）
    private static final String SCRIPT = """
            local key = KEYS[1]
            local window = tonumber(ARGV[1])
            local threshold = tonumber(ARGV[2])
            local now = tonumber(ARGV[3])
            local start = now - window * 1000

            -- 移除窗口外旧数据
            redis.call('ZREMRANGEBYSCORE', key, '-inf', start)

            -- 获取当前窗口请求数
            local count = redis.call('ZCARD', key)

            if count < threshold then
                -- 添加当前请求
                redis.call('ZADD', key, now, now)
                -- 设置过期时间（防止冷key长期占内存）
                redis.call('EXPIRE', key, window + 1)
                return 1
            else
                return 0
            end
            """;

    private final DefaultRedisScript<Long> script = new DefaultRedisScript<>(SCRIPT, Long.class);

    /**
     * 尝试请求
     * 
     * @param key 限流键（如 userId、IP）
     * @return true=允许，false=限流
     */
    public boolean tryAcquire(String key) {
        long now = System.currentTimeMillis();
        Long result = redisTemplate.execute(script, Collections.singletonList("rate:limit:" + key),
                "60", "100", String.valueOf(now) // 60s, 100次
        );
        return result != null && result == 1;
    }
}
