package com.colvir.bootcamp.rss.news;

import com.colvir.bootcamp.rss.news.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {TestConfig.class})
@TestPropertySource(properties = "scheduler-enabled=false")
class BootcampRssNewsApplicationTests {

    @Test
    void contextLoads() {
    }

}
