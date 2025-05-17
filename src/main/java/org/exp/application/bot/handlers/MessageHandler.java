package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.DeleteMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.application.bot.processes.CabinetService;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.services.main.TgUserService;
import org.exp.application.services.session.UserSessionService;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler implements DataHandler<Message> {

    private final TelegramBot bot;
    private final TgUserService tgUserService;
    private final CabinetService cabinetService;
    private final UserSessionService sessionService;

    @Override
    public void handle(Message message) {
        String text = message.text();
        Long userId = message.from().id();
        UserSession session = sessionService.getOrCreate(userId);

        if (text.equals("/start")) {

            if (session.getMessageId() != null) {
                bot.execute(new DeleteMessage(userId, session.getMessageId()));
                cabinetService.menuHome(userId);
                return;
            }

            tgUserService.getOrCreateTgUser(message);
            cabinetService.menuHome(userId);
            return;
        }
        bot.execute(new DeleteMessage(userId, session.getMessageId()));
        cabinetService.menuHome(userId);
        return;
    }
}