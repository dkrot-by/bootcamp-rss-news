package com.colvir.bootcamp.rss.news.mapper;

import com.colvir.bootcamp.rss.news.dto.NewsListResponse;
import com.colvir.bootcamp.rss.news.dto.NewsResponse;
import com.colvir.bootcamp.rss.news.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {

    List<NewsResponse> newsListToResponseList(List<News> news);

    default NewsListResponse newsListToResponse(List<News> news) {
        return new NewsListResponse(newsListToResponseList(news));
    }

}
