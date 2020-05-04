package com.yc.springboot.task2;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 多线程执行配置类
 * 源辰信息
 * @author Lydia
 * @2020年2月3日
 * 在传统的Spring项目中，我们可以在xml配置文件添加task的配置，而在SpringBoot项目中一般使用config配置类的方式添加配置，所以新建一个AsyncConfig类
 */
@Configuration // 表明该类是一个配置类
@EnableAsync // 开启异步事件的支持
public class AsyncConfig {
	// 此处成员变量应该使用@Value从配置中读取
	private int corePoolSize = 10;
	private int maxPoolSize = 200;
	private int queueCapacity = 10;
	
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.initialize();
		return executor;
	}
}
