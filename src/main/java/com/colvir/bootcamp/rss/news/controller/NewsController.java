/*
API для получения списков Новостей
В контроллере настроены вызовы по http и реализованы только вызовы сервисной части
 */
package com.colvir.bootcamp.rss.news.controller;

import com.colvir.bootcamp.rss.news.dto.NewsListResponse;
import com.colvir.bootcamp.rss.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    // Получение списка новостей за промежуток времени с beginDateTime по endDateTime включительно
    // Пример запроса выборки новостей с 2024-03-01 00:00:00 по 2024-03-01 23:59:59:
    // http://127.0.0.1:8080/api/news?beginDateTime=2024-03-01 00:00:00 +0300&endDateTime=2024-03-01 23:59:59 +0300
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public NewsListResponse getNewsByDates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss Z") Date beginDateTime,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss Z") Date endDateTime) {
        return newsService.getNewsByDates(beginDateTime, endDateTime);
    }

    // Получение последних новостей заданного количества
    // Пример запроса выборки 10-ти последних новостей:
    // http://127.0.0.1:8080/api/news/last/10
    @GetMapping("/last/{count}")
    @ResponseStatus(HttpStatus.OK)
    public NewsListResponse getLastNews(@PathVariable("count") Integer count) {
        return newsService.getLastNews(count);
    }

    // Получение количества новостей за указанную дату
    // Пример запроса количества новостей за 2024-03-01:
    // http://127.0.0.1:8080/api/news/count?date=2024-03-01
    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public Integer getCountNewsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return newsService.getCountNewsByDate(date);
    }

}
