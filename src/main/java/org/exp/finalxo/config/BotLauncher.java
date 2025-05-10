package org.exp.finalxo.config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.exp.finalxo.services.handlers.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Component
public class BotLauncher implements CommandLineRunner {

    private final TelegramBot bot;
    private final ExecutorConfig executorService;

    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;
    private final InlineHandler inlineHandler;
    private final ChosenInlineResultHandler chosenInlineResultHandler;

    @Override
    public void run(String... args) {
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                executorService.executorService().execute(() -> {
                    if (update.message() != null) {
                        messageHandler.handle(update.message());

                    } else if (update.callbackQuery() != null) {
                        callbackHandler.handle(update.callbackQuery());

                    } else if (update.inlineQuery() != null) {
                        inlineHandler.handle(update.inlineQuery());

                    } else if (update.chosenInlineResult() != null) {
                        chosenInlineResultHandler.handle(update.chosenInlineResult());

                    } else System.out.println("Unknown message - " + update);
                });
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    @Configuration
    public class BotConfig {
        @Value("${telegram.bot.token}")
        private String botToken;

        @Bean
        public TelegramBot telegramBot() {
            return new TelegramBot(botToken);
        }
    }

    @Configuration
    public class ExecutorConfig {
        @Bean
        public ExecutorService executorService() {
            return Executors.newFixedThreadPool(30);
        }
    }
}
