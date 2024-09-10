package com.colvir.bootcamp.rss.news.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Getter
public class Config {

    private final String databaseDriver;
    private final String databaseUrl;
    private final String databaseUsername;
    private final String databasePassword;
    private final String rssUrl;
    private final String rssPubDateFormat;
    private final String rssPubDateFormatLang;
    private final String rssPubDateFormatCountry;

    public Config(
            @Value("${spring.datasource.driver-class-name}") String databaseDriver,
            @Value("${spring.datasource.url}") String databaseUrl,
            @Value("${spring.datasource.username}") String databaseUsername,
            @Value("${spring.datasource.password}") String databasePassword,
            @Value("${rss.url}") String rssUrl,
            @Value("${rss.pub-date-format}") String rssPubDateFormat,
            @Value("${rss.pub-date-format-lang}") String rssPubDateFormatLang,
            @Value("${rss.pub-date-format-country}") String rssPubDateFormatCountry
    ) {
        this.databaseDriver = databaseDriver;
        this.databaseUrl = databaseUrl;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.rssUrl = rssUrl;
        this.rssPubDateFormat = rssPubDateFormat;
        this.rssPubDateFormatLang = rssPubDateFormatLang;
        this.rssPubDateFormatCountry = rssPubDateFormatCountry;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(databaseDriver);
        driverManagerDataSource.setUrl(databaseUrl);
        driverManagerDataSource.setUsername(databaseUsername);
        driverManagerDataSource.setPassword(databasePassword);
        return driverManagerDataSource;
    }

}
