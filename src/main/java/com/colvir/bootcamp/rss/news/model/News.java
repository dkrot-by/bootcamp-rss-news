/*
Сущность "Новость"
 */
package com.colvir.bootcamp.rss.news.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news")
public class News {

    @Id
    @Column(name = "guid", nullable = false)
    private String guid; // Идентификатор новости, который не должен генерироваться
    private String link; // Ссылка на новость
    private String title; // Заголовок новости
    @Column(name = "pub_date", nullable = false)
    private Date pubDate; // Дата и время публикации

}
