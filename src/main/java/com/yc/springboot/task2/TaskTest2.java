package com.yc.springboot.task2;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 源辰信息
 * @author Lydia
 * @2020年2月3日
 * 定时任务实现的几种方式：
 * 1、Timer：这是java自带的java.util.Timer类，这个类允许你调度一个java.util.TimerTask任务。使用这种方式可以让你的程序按照某一个频度执行，但不能在指定时间运行。一般用的较少。
 * 2、ScheduledExecutorService：也jdk自带的一个类；是基于线程池设计的定时任务类,每个调度任务都会分配到线程池中的一个线程去执行,也就是说,任务是并发执行,互不影响。
 * 3、Spring Task：Spring3.0以后自带的task，可以将它看成一个轻量级的Quartz，而且使用起来比Quartz简单许多。
 * 4、Quartz：这是一个功能比较强大的的调度器，可以让你的程序在指定时间执行，也可以按照某一个频度执行，配置起来稍显复杂。
 */
@Component
@EnableScheduling // 开启对定时任务的支持
@Async // 异步处理
public class TaskTest2 {
	/*
	// 默认是fixedDelay 上一次执行完毕时间后执行下一轮。每5秒执行一次
	@Scheduled(cron = "0/5 * * * * *")
	public void run() throws InterruptedException {
		System.out.println(Thread.currentThread().getName()+"=====>>>>>使用cron - run - "+(System.currentTimeMillis()/1000));
	}

	// 表示每个星期一中午21:07:00执行一次
	@Scheduled(cron = "0 09 21 ? * MON")
	public void run1() throws InterruptedException {
		System.out.println(Thread.currentThread().getName()+"=====>>>>>使用cron - run1 - "+(System.currentTimeMillis()/1000));
	}
	 */
	
	// fixedRate:上一次开始执行时间点之后5秒再执行
	@Scheduled(fixedRate = 5000)
	public void run2() throws InterruptedException {
		Thread.sleep(6000);
		System.out.println(Thread.currentThread().getName()+"=====>>>>>使用fixedRate - "+(System.currentTimeMillis()/1000));
	}

	// fixedDelay:上一次执行完毕时间点之后5秒再执行
	@Scheduled(fixedDelay = 5000)
	public void run3() throws InterruptedException {
		Thread.sleep(7000);
		System.out.println(Thread.currentThread().getName()+"=====>>>>>使用fixedDelay - "+(System.currentTimeMillis()/1000));
	}

	/**
	 * 第一次延迟2秒后执行，之后按fixedDelay的规则每5秒执行一次
	 */
	@Scheduled(initialDelay = 2000, fixedDelay = 5000)
	public void run4(){
		System.out.println(Thread.currentThread().getName()+"=====>>>>> 使用initialDelay - "+(System.currentTimeMillis()/1000));
	} 
}
