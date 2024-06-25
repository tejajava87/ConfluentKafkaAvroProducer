package com.kafka.producer.config;

import com.kafka.producer.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.TimeZone;

@Configuration
@EnableScheduling
@Slf4j
public class SchedulingConfig implements SchedulingConfigurer {

    @Value("${schedule.cron}")
    private String cronExpression;

    @Value("${schedule.timezone}")
    private String timeZone;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        TimeZone timeZone1 = TimeZone.getTimeZone(timeZone);
        CronTrigger cronTrigger = new CronTrigger(cronExpression, timeZone1);
        taskRegistrar.addTriggerTask(
                this::performScheduledTask,
                triggerContext -> cronTrigger.nextExecutionTime(triggerContext)
        );
    }

    public void performScheduledTask() {
        employeeService.processAndPublishRecords();
        log.info("Scheduled task executed at 4:30 PM Central Time");
    }
}
