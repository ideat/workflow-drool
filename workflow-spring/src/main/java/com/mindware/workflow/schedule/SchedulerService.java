package com.mindware.workflow.schedule;

import com.mindware.workflow.schedule.task.ScheduleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.security.acl.LastOwnerException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@EnableScheduling
@Configuration
public class SchedulerService implements SchedulingConfigurer {
    @Autowired
    ConfigurationService configurationService;

    @Autowired
    ScheduleTask scheduleTask;

    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        scheduler.setPoolSize(10);
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegister) {
        taskRegister.setScheduler(poolScheduler());
        taskRegister.addTriggerTask(() ->scheduleTask.expirationUserPassword() , t -> {
            Calendar nextExecutionTime = new GregorianCalendar();
            Date lasActualExecutionTime = t.lastActualExecutionTime();
            LocalDateTime localDateTime = LocalDateTime.now();
            int day = localDateTime.getDayOfMonth();
            int month = localDateTime.getMonthValue();
            int year = localDateTime.getYear();

            String value = configurationService.getConfiguration(Constants.CONFIG_RESET_USER_PASSWORD).getDescription();
            String[] parts = value.split(":");
            if(lasActualExecutionTime==null){
                nextExecutionTime.set(year, month-1,day,Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            }else{
                nextExecutionTime.setTime(lasActualExecutionTime != null ?lasActualExecutionTime: new Date() );
            }
            nextExecutionTime.add(Calendar.DAY_OF_YEAR,1); //Execute next day
            return nextExecutionTime.getTime();
        });

        taskRegister.addTriggerTask(() -> scheduleTask.notificationExceptions(), t ->{
            Calendar nextExecutionTime = new GregorianCalendar();
            Date lasActualExecutionTime = t.lastActualExecutionTime();
            LocalDateTime localDateTime = LocalDateTime.now();
            int day = localDateTime.getDayOfMonth();
            int month = localDateTime.getMonthValue();
            int year = localDateTime.getYear();

            String value = configurationService.getConfiguration(Constants.CONFIG_ALERT_EXPIRATION_EXCEPTIONS).getDescription();
            String[] parts = value.split(":");

            if(lasActualExecutionTime==null){
                nextExecutionTime.set(year, month-1,day,Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            }else{
                nextExecutionTime.setTime(lasActualExecutionTime != null ?lasActualExecutionTime: new Date() );
            }

            nextExecutionTime.add(Calendar.DAY_OF_YEAR,1); //Execute next day
            return nextExecutionTime.getTime();
        });

    }


}
