package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.application.bot.processes.CabinetCmd;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.entity.extra.BotLanguage;
import org.exp.application.repositories.TgUserRepository;
import org.exp.application.services.main.TgUserService;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler implements DataHandler<Message> {

    private TgUserService tgUserService;
    private CabinetCmd cabinetCmd;

    @Override
    public void handle(Message message) {
        String text = message.text();
        Optional<TgUser> optionalTgUser = tgUserService.getById(message.from().id());

        if (text.equals("/start")) {
            if (optionalTgUser.isPresent()) {
                cabinetCmd.handle(message);
            }


        }
    }
}