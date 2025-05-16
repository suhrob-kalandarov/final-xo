package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.model.CallbackQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.application.bot.processes.botgame.*;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallbackHandler implements DataHandler<CallbackQuery> {

    private final LanguageService languageService;
    private final BotGameService botGameService;
    private final BotGameDifficultyService difficultyService;
    private final BackButtonService backButtonService;
    private final BotGameModeService botGameModeService;
    private final SignMenuService signMenuService;

    @Override
    public void handle(CallbackQuery callbackQuery) {
        String data = callbackQuery.data();
        Long userId = callbackQuery.from().id();

        if (data.startsWith("bot-")){
            data = extractCleanData(data, "bot-");

            if (data==null) return;

            if (data.equals("game-difficulty")) {
                difficultyService.sendMenu(userId);

            } else if (data.startsWith("game-level_")) {
                difficultyService.setDifficulty(userId, data);

            } else if (data.equals("main-menu")) {
                botGameModeService.sendMenu(userId);

            } else if (data.equals("game-play")) {
                signMenuService.sendSignMenu(userId);

            } else if (data.startsWith("game-player-sign-")) {
                signMenuService.setSignAndSendBoard(data);

            } else if (data.startsWith("game-cell_")) {
                botGameService.handleMove(data);

            }

        } else if (data.startsWith("multi-")) {
            data = extractCleanData(data, "multi-");

            if (data==null) return;



        } else if (data.startsWith("user-")) {
            data = extractCleanData(data, "user-");

            if (data==null) return;

            if (data.startsWith("lang_")) {
                languageService.setBotLanguage(userId, data);

            } else if (data.equals("language")) {
                languageService.sendLangMenu(userId);

            } else if (data.startsWith("back-to_")) {
                backButtonService.execute(data, userId);
            }

        } else {
            log.error(data);
            log.info(callbackQuery.toString());
        }
    }

    private String extractCleanData(String data, String prefix) {
        return data.startsWith(prefix) ? data.substring(prefix.length()) : null;
    }

}