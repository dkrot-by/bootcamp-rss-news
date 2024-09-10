/*
Периодическое обновление базы новостей из внешнего источника
 */
package com.colvir.bootcamp.rss.news.scheduler;

import com.colvir.bootcamp.rss.news.service.RssNewsUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@ConditionalOnProperty(name = "scheduler-enabled", matchIfMissing = true)
@RequiredArgsConstructor
public class RssNewUpdateScheduler {

    private RssNewsUpdateService rssNewsUpdateService;

    @Autowired
    public RssNewUpdateScheduler(RssNewsUpdateService rssNewsUpdateService) {
        this.rssNewsUpdateService = rssNewsUpdateService;
    }

    // Периодичность обновления задается настройкой rss.update-scheduler-time, по умолчанию раз в час
    @Scheduled(fixedDelayString = "${rss.update-scheduler-time: PT01H}")
    public void scheduledParseNews() {
        rssNewsUpdateService.updateRssNewsFromHttp();
    }

}
