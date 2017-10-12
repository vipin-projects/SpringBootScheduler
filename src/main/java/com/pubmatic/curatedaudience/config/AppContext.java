package com.pubmatic.curatedaudience.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
@EnableAsync
public class AppContext {

	private static AtomicInteger COUNTER = new AtomicInteger(0);

	private static AtomicInteger TASK_COUNTER = new AtomicInteger(0);

	@Bean
	public Executor taskExecutor(@Value("${scheduled.thread.pool.size:10}") int threadPoolSize) {
		return Executors.newFixedThreadPool(threadPoolSize, (Runnable r) -> {
			Thread t = new Thread(r);
			t.setName("scheduled-executor-" + COUNTER.incrementAndGet());
			return t;
		});
	}

	@Bean
	public TaskScheduler taskScheduler(@Value("${task.scheduler.thread.pool.size:10}") int threadPoolSize) {
		return new ConcurrentTaskScheduler(Executors.newScheduledThreadPool(threadPoolSize, (Runnable r) -> {
			Thread t = new Thread(r);
			t.setName("ask-executor-" + TASK_COUNTER.incrementAndGet());
			return t;
		}));
	}
}