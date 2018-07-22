package com.money.tap.scheduler;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskListener implements SchedulerListener {

	private static final Logger log = LoggerFactory.getLogger(TaskListener.class);

	private Scheduler scheduler = null;

	public TaskListener(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public void jobScheduled(Trigger trigger) {
		log.info("Job Scheduled: ");
	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {
		log.info("Job Unscheduled: ");
	}

	@Override
	public void triggerFinalized(Trigger trigger) {
		log.info("triggerFinalized: ");
	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {

	}

	@Override
	public void triggersPaused(String triggerGroup) {

	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {

	}

	@Override
	public void triggersResumed(String triggerGroup) {

	}

	@Override
	public void jobAdded(JobDetail jobDetail) {

	}

	@Override
	public void jobDeleted(JobKey jobKey) {
		log.info("Job Deleted : " + jobKey.toString());
	}

	@Override
	public void jobPaused(JobKey jobKey) {

	}

	@Override
	public void jobsPaused(String jobGroup) {

	}

	@Override
	public void jobResumed(JobKey jobKey) {

	}

	@Override
	public void jobsResumed(String jobGroup) {

	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {

	}

	@Override
	public void schedulerInStandbyMode() {

	}

	@Override
	public void schedulerStarted() {

	}

	@Override
	public void schedulerStarting() {

	}

	@Override
	public void schedulerShutdown() {

	}

	@Override
	public void schedulerShuttingdown() {

	}

	@Override
	public void schedulingDataCleared() {

	}

}
