package com.goncharov.tinyurl.scheduler;

import com.goncharov.tinyurl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class Scheduler {
    private final UrlService urlService;

    @Autowired
    public Scheduler(UrlService urlService) {
        this.urlService = urlService;
    }

    @Scheduled(fixedRate = 10_000)
    public void scheduleDelete() {
        urlService.deleteAllByExpirationDateBefore(LocalDateTime.now());
    }
}
