/*
Ответ сервиса: Список новостей
 */
package com.colvir.bootcamp.rss.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewsListResponse {

    private List<NewsResponse> newsList;

}
