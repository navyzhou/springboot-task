package com.yc.springboot.task;

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
//@EnableScheduling // 开启对定时任务的支持
public class TaskTest {
	/*
	 * cron从左到右用空格隔开：秒 分 小时 月份中日期 月份 星期中的日期 年份
	 * 我们可以给定6个或7个 即：
	 * 	Seconds Minutes Hours DayOfMonth Month DayOfWeeK Year 或  Seconds Minutes Hours DayOfMonth Month DayOfWeeK Year
	 *    * : 表示匹配该域的任意一个值
	 *    ? : 只能用于DayOfMonth 或 DayOfWeek两个域。它也可以匹配域的任意值，但实际不会。因为DayOfMonth和DayOfWeek会相互影响。例如：
	 *    	现在每个月的20日触发任务，不管20日到底是星期几，则只能使用如下写法：0 0 0 20 * ?，其中最后一位只能用?
	 *    - : 表示范围，例如在Minutes域使用 5-20，表示从5分钟到20分钟每分钟触发一次
	 *    / : 表示起始时间开始触发，然后每个固定时间触发一次。例如在Minutes域使用 5/20，意味着5分钟后触发一次，然后每隔20分钟触发一次
	 *    , : 表示列出枚举值。例如：在Minutes域使用5,20，则意味着在5和20分钟的时候触发
	 *    L : 表示最后，只能出现在DayOfWeek 或 DayOfMonth域，如果用在DayOfWeek域上使用5L，意味着在最后一个星期四触发
	 *    W : 表示有效工作日(周一到周五)，只能出现在DayOfMonth域，系统就在离指定日期的最近的有效工作日触发。例如：
	 *    	在DayOfMonth使用5W，如果5日是星期天，则将在最近的工作日6号星期一触发。如果5日是星期一到星期五中的一天，则就在5日触发。
	 *    LW: 表示在某个月最后最后一个工作日，即最后一个星期五
	 *    # : 用于确定每个月的第几个星期几，只能出现在DayOfMonth域。例如：4#2，表示某月的第二个星期三
	 *    
	 *    (1)0 0 2 1 * ? *   表示在每月的1日的凌晨2点调整任务
	 *    (2)0 15 10 ? * MON-FRI   表示周一到周五每天上午10:15执行作业
	 *    (3)0 15 10 ? 6L 2002-2006   表示2002-2006年的每个月的最后一个星期五上午10:15执行作
	 *    (4)0 0 10,14,16 * * ?   每天上午10点，下午2点，4点 
	 *    (5)0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时 
	 *    (6)0 0 12 ? * WED    表示每个星期三中午12点 
	 *    (7)0 0 12 * * ?   每天中午12点触发 
	 *    (8)0 15 10 ? * *    每天上午10:15触发 
	 *    (9)0 15 10 * * ?     每天上午10:15触发 
	 *    (10)0 15 10 * * ? *    每天上午10:15触发 
	 *    (11)0 15 10 * * ? 2005    2005年的每天上午10:15触发 
	 *    (12)0 * 14 * * ?     在每天下午2点到下午2:59期间的每1分钟触发 
	 *    (13)0 0/5 14 * * ?    在每天下午2点到下午2:55期间的每5分钟触发 
	 *    (14)0 0/5 14,18 * * ?     在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 
	 *    (15)0 0-5 14 * * ?    在每天下午2点到下午2:05期间的每1分钟触发 
	 *    (16)0 10,44 14 ? 3 WED    每年三月的星期三的下午2:10和2:44触发 
	 *    (17)0 15 10 ? * MON-FRI    周一至周五的上午10:15触发 
	 *    (18)0 15 10 15 * ?    每月15日上午10:15触发 
	 *    (19)0 15 10 L * ?    每月最后一日的上午10:15触发 
	 *    (20)0 15 10 ? * 6L    每月的最后一个星期五上午10:15触发 
	 *    (21)0 15 10 ? * 6L 2002-2005   2002年至2005年的每月的最后一个星期五上午10:15触发 
	 *    (22)0 15 10 ? * 6#3   每月的第三个星期五上午10:15触发
	 */
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
*/
	/**
	 * 第一次延迟2秒后执行，之后按fixedDelay的规则每5秒执行一次
	 */
    @Scheduled(initialDelay = 2000, fixedDelay = 5000)
    public void run4(){
        System.out.println(Thread.currentThread().getName()+"=====>>>>> 使用initialDelay - "+(System.currentTimeMillis()/1000));
    } 
    
    // 问题:使同一个线程中串行执行，如果只有一个定时任务，这样做肯定没问题，当定时任务增多，如果一个任务卡死，会导致其他任务也无法执行。解决方案多线程执行
}
