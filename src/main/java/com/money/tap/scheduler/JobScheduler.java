package com.money.tap.scheduler;

import java.util.Calendar;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.money.tap.service.PriceGenerator;

public class JobScheduler {

	private static final Logger log = LoggerFactory.getLogger(JobScheduler.class);

	private Scheduler scheduler = null;

	/**
	 * constructor
	 */
	public JobScheduler() {
		super();
		_initialize();
	}

	/**
	 * 
	 * @return
	 */
	public Scheduler scheduler() {
		return this.scheduler;
	}

	/**
	 * initialize the scheduler
	 */
	public void _initialize() {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		try {
			this.scheduler = schedulerFactory.getScheduler();
			this.scheduler.getListenerManager().addSchedulerListener(new TaskListener(this.scheduler));
			this.scheduler.start();
		} catch (SchedulerException e) {
			log.error("Error while initializing the scheduler: ", e);
		}
	}

	/**
	 * 
	 * this method has to schedule main job
	 * 
	 * @param name
	 * @param time
	 */
	public void scheduleMainJob(String name, String time) {
		try {
			CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(time)
					.inTimeZone(Calendar.getInstance().getTimeZone());
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, name).withSchedule(cronSchedule).build();
			this.scheduler.scheduleJob(this.getJobDetail(name, name, null, PriceGenerator.class), trigger);
		} catch (SchedulerException e) {
			log.error("Error in scheduleMainJob: ", e);
		}
	}

	/**
	 * 
	 * getJobDetail method
	 * 
	 * @param name
	 * @param group
	 * @param data
	 * @param clazz
	 * @return
	 */
	public JobDetail getJobDetail(String name, String group, Object data, Class clazz) {
		JobDataMap jobData = new JobDataMap();
		JobDetail jobDetail = null;
		try {
			jobData.put("data", data);
			jobDetail = JobBuilder.newJob(clazz).usingJobData(jobData).withIdentity(new JobKey(name, group)).build();
		} catch (Exception e) {
			log.error("Exception in getJobDetail: ", e);
		}
		return jobDetail;
	}

	/**
	 * clear schedule
	 */
	public void clear() {
		try {
			this.scheduler.clear();
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
	}
}
