package org.exp.application;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    public static class BotConfig {
        @Value("${telegram.bot.token}")
        private String botToken;

        @Bean
        public TelegramBot telegramBot() {
            return new TelegramBot(botToken);
        }
    }

    @Configuration
    public static class ExecutorConfig {
        @Bean
        public ExecutorService executorService() {
            return Executors.newFixedThreadPool(30);
        }
    }
}
