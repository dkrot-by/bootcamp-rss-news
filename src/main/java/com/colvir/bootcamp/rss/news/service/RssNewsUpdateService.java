/*
Сервис обновления новостей из внешнего источника
Ссылка внешнего источника хранится в настройке rss.url
Парсер новостей - RssXmlParserHandler
 */
package com.colvir.bootcamp.rss.news.service;

import com.colvir.bootcamp.rss.news.config.Config;
import com.colvir.bootcamp.rss.news.handler.RssXmlParserHandler;
import com.colvir.bootcamp.rss.news.model.News;
import com.colvir.bootcamp.rss.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RssNewsUpdateService {

    private final Config config;
    private final Logger log = LoggerFactory.getLogger(RssNewsUpdateService.class);
    private final NewsRepository newsRepository;

    public void updateRssNewsFromHttp() {
        try {
            // Забираем XML с RSS по ссылке из настройки и отдаем ее парсеру RssXmlParserHandler через SAXParser
            // На выходе получаем список новостей (News)
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            RssXmlParserHandler handler = new RssXmlParserHandler(config);
            URL url = new URL(config.getRssUrl());
            parser.parse(url.openStream(), handler);
            ArrayList<News> newsList = handler.getNewsList();
            for (News news : newsList) {
                // Сохраняем только новость, которой нет в БД
                if (newsRepository.findAllByGuid(news.getGuid()).isEmpty())
                    newsRepository.save(news);
            }
            log.info(String.format("rss news successfully updated from %s", config.getRssUrl()));
        } catch ( IOException | ParserConfigurationException | SAXException e ) {
            // Ошибку обновления пишем в лог и работаем дальше
            log.error(e.getMessage());
        }
    }

}
