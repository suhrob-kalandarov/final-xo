package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.model.CallbackQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.application.bot.processes.BackButtonService;
import org.exp.application.bot.processes.BotGameService;
import org.exp.application.bot.processes.LanguageService;
import org.exp.application.bot.processes.ModeService;
import org.exp.application.services.GameCenter;
import org.exp.application.services.main.TgUserService;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallbackHandler implements DataHandler<CallbackQuery> {

    private final TgUserService tgUserService;
    private final LanguageService languageService;
    private final ModeService modeService;
    private final GameCenter gameCenter;
    private final BotGameService botGameService;
    private final BackButtonService backButtonService;

    @Override
    public void handle(CallbackQuery callbackQuery) {
        String data = callbackQuery.data();
        Long userId = callbackQuery.from().id();

        if (data.startsWith("LANG_")) {
            languageService.setBotLanguage(userId, data);

        } else if (data.equals("language")) {
            languageService.sendLangMenu(userId);

        } else if (data.equals("game-mode")) {
            modeService.sendMainMenu(userId);

        } else if (data.equals("play-with_bot")) {
            modeService.sendSignMenu(userId);

        } else if (data.startsWith("play_")) {
            modeService.sendSignMenu(data);

        } else if (data.startsWith("sign-")) {
            gameCenter.setSignAndSendBoard(data);

        } else if (data.startsWith("MOVE_")) {
            botGameService.handleMove(data);

        } else if (data.startsWith("back-to_")) {
            backButtonService.execute(data, userId);
        }
    }
}