package com.money.tap.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.money.tap.common.Global;
import com.money.tap.scheduler.JobScheduler;

@Configuration
public class Control {

	private static final Logger log = LoggerFactory.getLogger(Control.class);

	public static void _initialize() {
		try {
			JobScheduler schedule = new JobScheduler();
			schedule.scheduleMainJob("Post Bitcoin", Global.TIMER);
		} catch (Exception e) {
			log.error("Exception in initializing scheduled job: ", e);
		}
	}

}
