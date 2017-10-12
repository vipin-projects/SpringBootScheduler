package com.pubmatic.curatedaudience.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pubmatic.curatedaudience.JobExecutor;
import com.pubmatic.curatedaudience.util.DateUtil;

@Component
public class ScheduledTasks {
	
	@Autowired
	JobExecutor jobExecutor;
	
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //@Scheduled(fixedRate = 5000)
    @Scheduled(cron = "${curatedScript.schedule}")
    public void jobScheduler() {
    	logger.info("Scheduling Curated Audience at - {}", DateUtil.getTodayDate(DATE_FORMAT));
    	jobExecutor.execute();
    }
}
