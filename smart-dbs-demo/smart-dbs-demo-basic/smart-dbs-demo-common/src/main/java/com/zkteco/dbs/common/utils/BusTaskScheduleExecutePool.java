package com.zkteco.dbs.common.utils;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * BusTaskScheduleExecutePool
 *
 * @author sheldon.wu
 * @date 2020/11/30 14:18
 * @since 1.0.0
 */
@Configuration
@EnableAsync
public class BusTaskScheduleExecutePool {

    @Value("${dbs.schedulerPool.poolSize:5}")
    private int poolSize;

    private ThreadPoolTaskScheduler executor;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        if (this.executor != null) {
            return this.executor;
        }
        this.executor = new ThreadPoolTaskScheduler();
        this.executor.setThreadNamePrefix("SchedulerExecutor-");
        this.executor.setPoolSize(poolSize);
        //设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        this.executor.setWaitForTasksToCompleteOnShutdown(true);
        //设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        this.executor.setAwaitTerminationSeconds(60);
        //这里采用了CallerRunsPolicy策略，当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
        this.executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    public ThreadPoolTaskScheduler getScheduler() {
        return this.executor;
    }

}
