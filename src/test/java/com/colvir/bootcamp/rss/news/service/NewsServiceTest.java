package com.colvir.bootcamp.rss.news.service;

import com.colvir.bootcamp.rss.news.config.TestConfig;
import com.colvir.bootcamp.rss.news.dto.NewsListResponse;
import com.colvir.bootcamp.rss.news.dto.NewsResponse;
import com.colvir.bootcamp.rss.news.mapper.NewsMapper;
import com.colvir.bootcamp.rss.news.model.News;
import com.colvir.bootcamp.rss.news.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        NewsService.class,
        NewsMapper.class
})
@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("test")
public class NewsServiceTest {

    @Autowired
    private NewsService newsService;

    @MockBean
    private NewsRepository newsRepository;

    // Тестовые данные
    private final Date beginDateTime = Date.from(LocalDateTime.parse("2024-03-01T01:00:00",
            DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.systemDefault()).toInstant());
    private final Date endDateTime = Date.from(LocalDateTime.parse("2024-03-01T10:30:00",
            DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.systemDefault()).toInstant());
    private final Integer countNews = 2;
    private final Date dateIn = Date.from(LocalDate.parse("2024-03-01",
            DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay(ZoneId.systemDefault()).toInstant());

    private final Date pubDate1 = Date.from(LocalDateTime.parse("2024-03-01T01:12:30",
            DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.systemDefault()).toInstant());
    private final Date pubDate2 = Date.from(LocalDateTime.parse("2024-03-01T02:40:25",
            DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.systemDefault()).toInstant());

    private final List<News> newsList = Arrays.asList(
            new News("guid2", "link2", "title2", pubDate2),
            new News("guid1", "link1", "title1", pubDate1));
    private final NewsListResponse expectedResponse = new NewsListResponse(Arrays.asList(
            new NewsResponse(pubDate2, "title2", "link2"),
            new NewsResponse(pubDate1, "title1", "link1")));

    // Заглушки для репозитория
    @BeforeEach
    private void PrepareData() {
        when(newsRepository.findAllByDates(beginDateTime, endDateTime)).thenReturn(newsList);
        when(newsRepository.findAllLast(countNews)).thenReturn(newsList);
        when(newsRepository.getCountByDate(dateIn)).thenReturn(countNews);
    }

    // Получение списка новостей за промежуток времени
    @Test
    void getNewsByDates_success() {
        // Тест
        NewsListResponse actualResponse = newsService.getNewsByDates(beginDateTime, endDateTime);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(newsRepository, Mockito.times(1)).findAllByDates(beginDateTime, endDateTime);
    }

    // Получение count последних новостей
    @Test
    void getLastNews_success() {
        // Тест
        NewsListResponse actualResponse = newsService.getLastNews(countNews);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(newsRepository, Mockito.times(1)).findAllLast(countNews);
    }

    // Аггрегированная информация - количество новостей на дату date
    @Test
    void getCountNewsByDate_success() {
        // Тест
        Integer actualResponse = newsService.getCountNewsByDate(dateIn);
        // Проверка результата
        assertEquals(countNews, actualResponse);
        verify(newsRepository, Mockito.times(1)).getCountByDate(dateIn);
    }

}
