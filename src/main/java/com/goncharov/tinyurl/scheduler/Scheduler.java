package com.goncharov.tinyurl.scheduler;

import com.goncharov.tinyurl.entity.Url;
import com.goncharov.tinyurl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class Scheduler {
    private final UrlService urlService;

    @Autowired
    public Scheduler(UrlService urlService) {
        this.urlService = urlService;
    }

    @Scheduled(fixedRate = 60000)
    public void scheduleDelete() {
        List<Url> urls = urlService.findAllByExpirationDateBefore(LocalDateTime.now());
        urls.forEach(urlService::deleteUrl);
    }
}
