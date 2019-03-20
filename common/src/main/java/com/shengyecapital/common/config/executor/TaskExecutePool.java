package com.shengyecapital.common.config.executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义 TaskExecute
 *
 * @author long.luo
 * @date 2019/1/3 9:26
 */
@Configuration
public class TaskExecutePool {

    private static final int CORE_POOL_SIZE = 15;

    private static final int MAX_POOL_SIZE = 40;

    private static final int KEEP_ALIVE_SECONDS = 300;

    private static final int QUEUE_CAPACITY = 40;

    private static final String THREAD_NAME_PREFIX = "MyExecutor-";

    @Bean
    public Executor myTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(CORE_POOL_SIZE);
        //核心线程池大小
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        //队列容量
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        //活跃时间
        executor.setQueueCapacity(QUEUE_CAPACITY);
        //线程名字前缀
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        // 线程池对拒绝任务的处理策略
        // CallerRunsPolicy 不在新线程中执行任务，而是由调用者所在的线程来执行 如果调用者所在的线程已关闭，则会丢弃该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }

}
