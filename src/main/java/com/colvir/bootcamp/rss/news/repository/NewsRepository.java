/*
Репозиторий для работы с Новостями:
  - Хранение реализовано в БД
 */
package com.colvir.bootcamp.rss.news.repository;

import com.colvir.bootcamp.rss.news.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    // Список новостей по guid
    List<News> findAllByGuid(String guid);

    // Получение списка новостей за промежуток времени с beginDateTime по endDateTime включительно
    @Query(value = "SELECT n.* FROM news n WHERE n.pub_date BETWEEN CAST(:beginDateTime as TIMESTAMP WITH TIME ZONE) AND CAST(:endDateTime as TIMESTAMP WITH TIME ZONE) ORDER BY n.pub_date DESC",  nativeQuery = true)
    List<News> findAllByDates(Date beginDateTime, Date endDateTime);

    // Получение count последних новостей
    @Query(value = "SELECT n.* FROM news n ORDER BY n.pub_date DESC LIMIT :count", nativeQuery = true)
    List<News> findAllLast(Integer count);

    // Аггрегированная информация - количество новостей на дату date
    @Query(value = "SELECT COUNT(*) FROM news n WHERE CAST(n.pub_date AS DATE) = CAST(:date AS DATE)", nativeQuery = true)
    Integer getCountByDate(Date date);

}
