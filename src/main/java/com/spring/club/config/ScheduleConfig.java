package com.spring.club.config;

import com.spring.club.services.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleConfig {
    @Autowired
    private SeasonService seasonService;

    @Scheduled(cron = "0 0 0 31 9 *")
    public void scheduleUpdate() {
        seasonService.updateSeason();
    }
}