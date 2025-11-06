package Spring;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.logging.Logger;

public class CompletableFutureUtils {
    private static final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();
    // 1. 修复：定义日志
    private static final Logger log = Logger.getLogger(CompletableFutureUtils.class.getName());

    // 2. 修复：定义简单监控
    private static final class Metric {
        private static final AtomicLong success = new AtomicLong();
        private static final AtomicLong fail = new AtomicLong();

        static void success() {
            success.incrementAndGet();
            log.info("Success: " + success.get());
        }

        static void fail() {
            fail.incrementAndGet();
            log.warning("Fail: " + fail.get());
        }
    }

    // 3. 修复：riskyTask 改为 static，正确处理中断
    private static String riskyTask() {
        try {
            log.info("开始执行耗时任务...");
            Thread.sleep(5000);
            return "任务完成";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 保留中断状态
            throw new RuntimeException("任务被中断", e);
        }
    }

    public static void main(String[] args) throws Exception {
        CompletableFuture<String> result =
                CompletableFuture.supplyAsync(CompletableFutureUtils::riskyTask) // 修复：用类名::
                        // .orTimeout(3, TimeUnit.SECONDS) // JDK9+，JDK8 注释掉

                        // 4. 修复：JDK8 手动超时
                        .applyToEither(timeoutAfter(3, TimeUnit.SECONDS), r -> r)

                        .handle((r, ex) -> {
                            if (ex instanceof TimeoutException) {
                                log.warning("任务超时");
                                return "TIMEOUT";
                            } else if (ex != null) {
                                log.severe("任务异常: " + ex.getMessage());
                                return "ERROR";
                            }
                            return r;
                        }).whenComplete((r, ex) -> {
                            if (ex == null)
                                Metric.success();
                            else
                                Metric.fail();
                        });

        // 5. 修复：必须触发执行
        System.out.println("最终结果: " + result.join());

        // 可选：优雅关闭
        // Executors.defaultThreadFactory()... 不需要手动关
        CompletableFuture<String> task = CompletableFuture.supplyAsync(() -> {
            // 模拟随机异常 + 慢任务
            if (Math.random() > 0.5)
                throw new IllegalStateException("随机错误");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ignored) {
            }
            return "SUCCESS";
        });

        CompletableFuture<String> result1 =
                withTimeout(task, 2, TimeUnit.SECONDS).handle((r, ex) -> {
                    if (ex != null) {
                        if (ex.getCause() instanceof TimeoutException) {
                            return "TIMEOUT_FALLBACK";
                        }
                        System.err.println("业务异常: " + ex.getMessage());
                        return "ERROR_FALLBACK";
                    }
                    return r;
                });

        System.out.println("最终: " + result1.join());
    }

    // 手动超时工具方法（JDK8 兼容）
    private static <T> CompletableFuture<T> timeoutAfter(long timeout, TimeUnit unit) {
        CompletableFuture<T> result = new CompletableFuture<>();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> result.completeExceptionally(new TimeoutException()), timeout,
                unit);
        executor.shutdown();
        return result;
    }

    /** 为任意 CompletableFuture 添加超时 */
    public static <T> CompletableFuture<T> withTimeout(CompletableFuture<T> future, long timeout,
            TimeUnit unit) {
        CompletableFuture<T> timeoutFence = new CompletableFuture<>();
        scheduler
                .schedule(
                        () -> timeoutFence.completeExceptionally(
                                new TimeoutException("操作超时: " + timeout + " " + unit)),
                        timeout, unit);

        // 谁先完成就取消另一个
        return future.applyToEither(timeoutFence, Function.identity());
    }
}
