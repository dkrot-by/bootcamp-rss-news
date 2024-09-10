/*
Сервис для работы с новостями
Доступны различные выборки новостей
 */
package com.colvir.bootcamp.rss.news.service;

import com.colvir.bootcamp.rss.news.dto.NewsListResponse;
import com.colvir.bootcamp.rss.news.mapper.NewsMapper;
import com.colvir.bootcamp.rss.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsMapper newsMapper;
    private final NewsRepository newsRepository;

    // Получение списка новостей за промежуток времени с beginDateTime по endDateTime включительно
    public NewsListResponse getNewsByDates(Date beginDateTime, Date endDateTime) {
        return newsMapper.newsListToResponse(newsRepository.findAllByDates(beginDateTime, endDateTime));
    }

    // Получение count последних новостей
    public NewsListResponse getLastNews(Integer count) {
        return newsMapper.newsListToResponse(newsRepository.findAllLast(count));
    }

    // Аггрегированная информация - количество новостей на дату date
    public Integer getCountNewsByDate(Date date) {
        return newsRepository.getCountByDate(date);
    }

}
