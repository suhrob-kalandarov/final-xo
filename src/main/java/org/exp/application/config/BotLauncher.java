package org.exp.application.config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.exp.application.Application;
import org.exp.application.bot.handlers.CallbackHandler;
import org.exp.application.bot.handlers.ChosenInlineResultHandler;
import org.exp.application.bot.handlers.InlineHandler;
import org.exp.application.bot.handlers.MessageHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BotLauncher implements CommandLineRunner {

    private final TelegramBot bot;
    private final Application.ExecutorConfig executorService;

    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;
    private final InlineHandler inlineHandler;
    private final ChosenInlineResultHandler chosenInlineResultHandler;

    @Override
    public void run(String... args) {
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                executorService.executorService().execute(() -> {
                    System.out.println("WWW update.chosenInlineResult() = " + update.chosenInlineResult());
                    if (update.message() != null) {
                        messageHandler.handle(update.message());

                    } else if (update.callbackQuery() != null) {
                        callbackHandler.handle(update.callbackQuery());

                    } else if (update.inlineQuery() != null) {
                        inlineHandler.handle(update.inlineQuery());

                    } else if (update.chosenInlineResult() != null) {
                        chosenInlineResultHandler.handle(update.chosenInlineResult());

                    } else System.err.println("Unknown message -> " + update);
                });
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
