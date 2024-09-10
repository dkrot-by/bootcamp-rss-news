/*
Парсер новостей из потока (stream) xml в список объектов News
Для парсинга подходят rss любого канала, для которого определана структура:
  1) Новость заключена в теги <item> ... </item>
  2) Обязательное название новости внутри item, заключено в тегах <title> ... </title>
  3) Обязательная ссылка на новость внутри item, заключена в тегах <link> ... </link>
  4) Обязательный уникальный идентификатор записи внутри item, заключен в тегах <guid> ... </guid>
  5) Обязательная дата и время новости внутри item, заключены в тегах <pubDate> ... </pubDate>
     формат даты указывается в настройке rss.pub-date-format
     язык и страна локализации указывается в настройках rss.pub-date-format-lang и rss.pub-date-format-country
 */
package com.colvir.bootcamp.rss.news.handler;

import com.colvir.bootcamp.rss.news.config.Config;
import com.colvir.bootcamp.rss.news.model.News;
import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

@Getter
public class RssXmlParserHandler extends DefaultHandler {

    static final String ITEM = "item";
    static final String TITLE = "title";
    static final String LINK = "link";
    static final String GUID = "guid";
    static final String PUB_DATE = "pubDate";

    private final Config config;
    private final ArrayList<News> newsList = new ArrayList<>();

    private String startTag;
    private String title;
    private String link;
    private String guid;
    private String pubDate;


    public RssXmlParserHandler(Config config) {
        this.config = config;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        // Текущий открывающий тег
        startTag = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        // Смотрим каждый тег: если он с нужной информацией, записываем ее в атрибуты парсера
        // (characters вызывается после каждого тега и обрабатывает текст между тегами: текущий и следующий)
        String string = new String(ch, start, length);
        if (!string.isEmpty()) {
            switch (startTag) {
                case TITLE -> title = string;
                case LINK -> link = string;
                case GUID -> guid = string;
                case PUB_DATE -> pubDate = string;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        // Сбросим стартовый тег, т.к. characters вызывается после открывающего и закрывающего тега
        startTag = "";
        // Если закрываюший тег и все атрибуты присутствовали в item, то добавляем в список новый news
        if (Objects.equals(qName, ITEM)) {
            if ((title != null && !title.isEmpty()) && (link != null && !link.isEmpty())
                    && (guid != null && !guid.isEmpty()) && (pubDate != null && !pubDate.isEmpty())) {
                // Парсим дату согласно настроек
                SimpleDateFormat dateFormat = new SimpleDateFormat(config.getRssPubDateFormat(),
                        new Locale(config.getRssPubDateFormatLang(), config.getRssPubDateFormatCountry()));
                try {
                    newsList.add(new News(guid, link, title, dateFormat.parse(pubDate)));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                // тег item закрылся, поэтому чистим атрибуты парсера, чтобы при нарушеной структуре xml
                // битые данные не соединились
                title = null;
                link = null;
                guid = null;
                pubDate = null;
            }
        }
    }

}
