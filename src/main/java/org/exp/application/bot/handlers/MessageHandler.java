package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.application.bot.processes.CabinetService;
import org.exp.application.models.entity.TgUser;
import org.exp.application.services.main.TgUserService;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler implements DataHandler<Message> {

    private final TgUserService tgUserService;
    private final CabinetService cabinetService;

    @Override
    public void handle(Message message) {
        String text = message.text();
        Long userId = message.from().id();
        TgUser tgUser = tgUserService.getOrCreateTgUser(message);

        if (text.equals("/start")) {

            /*if (tgUser.getMessageId() != null) {
                cabinetService.editSendHome(tgUser);
            }*/

            cabinetService.menuHome(userId);
        }
    }
}