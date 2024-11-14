package ru.test.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.test.controller.TestController;

@TestConfiguration
@EnableWebMvc
public class TestConfig {

    @Bean
    public TestController testController() {
        return new TestController();
    }
}
