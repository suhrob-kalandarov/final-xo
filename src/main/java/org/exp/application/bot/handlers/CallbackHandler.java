package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.model.CallbackQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.application.bot.processes.LanguageService;
import org.exp.application.bot.processes.ModeService;
import org.exp.application.models.entity.TgUser;
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

    @Override
    public void handle(CallbackQuery callbackQuery) {
        String data = callbackQuery.data();
        Long userId = callbackQuery.from().id();
        TgUser tgUser = tgUserService.getById(userId);

        if (data.startsWith("LANG_")) {
            languageService.setBotLanguage(userId, data);

        } else if (data.equals("game-mode")) {
            modeService.sendMainMenu(tgUser);

        } else if (data.equals("play-with_bot")) {
            modeService.sendSignMenu(tgUser);

        } else if (data.startsWith("play-with_")) {


        }
    }
}