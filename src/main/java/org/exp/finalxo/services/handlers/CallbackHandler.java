package org.exp.finalxo.services.handlers;

import com.pengrad.telegrambot.model.CallbackQuery;
import org.exp.xo3bot.dtos.MainDto;
import org.exp.xo3bot.entity.User;
import org.exp.xo3bot.processes.callbackquery.botgame.DifficultyChangerMenuCmd;
import org.exp.xo3bot.processes.callbackquery.botgame.DifficultyMenuCmd;
import org.exp.xo3bot.processes.callbackquery.botgame.LanguageChangerMenuCmd;
import org.exp.xo3bot.processes.callbackquery.botgame.LanguageMenuCmd;
import org.exp.xo3bot.processes.callbackquery.multigame.MultiGameCmd;
import org.exp.xo3bot.services.user.UserService;
import org.exp.xo3bot.usekeys.Handler;
import org.exp.xo3bot.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.exp.xo3bot.utils.Constants.*;

@Component
public class CallbackHandler implements Handler<CallbackQuery> {

    @Autowired
    private MainDto dto;

    @Override
    public void handle(CallbackQuery callbackQuery) {
        Runnable process = null;
        String data = callbackQuery.data();
        User user = new UserService(dto.getUserRepository()).getOrCreateTelegramUser(callbackQuery);

        if (data.startsWith("SELECT_X_") || data.startsWith("SELECT_O_") || data.startsWith("MOVE_")) {
            process = new MultiGameCmd(dto, callbackQuery);

        } else if (data.startsWith(LANG)) {
            process = new LanguageChangerMenuCmd(user, data, dto);

        } else if (data.equals(LANGUAGE_MSG)) {
            process = new LanguageMenuCmd(user, dto);

        } else if (data.equals(Constants.DIFFICULTY_LEVEL_BTN)) {
            process = new DifficultyMenuCmd(user, dto);

        } else if (data.startsWith(Constants.LEVEL)) {
            process = new DifficultyChangerMenuCmd(user, data, dto);

        }
        Objects.requireNonNull(process).run();
    }
}
