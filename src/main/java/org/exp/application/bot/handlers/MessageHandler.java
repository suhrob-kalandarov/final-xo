package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.application.bot.processes.CabinetService;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.services.user.TgUserService;
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

        try {
            if (text != null && text.equals("/start")) {
                if (session.getMessageId() != null) {
                    bot.execute(new DeleteMessage(userId, session.getMessageId()));
                    cabinetService.menuHome(userId);
                    return;
                }

                TgUser tgUser = tgUserService.getOrCreateTgUser(message);
                log.info("Processed /start for user ID: {}, Fullname: {}", tgUser.getId(), tgUser.getFullname());
                cabinetService.menuHome(userId);
                return;
            }

            if (session.getMessageId() != null) {
                bot.execute(new DeleteMessage(userId, session.getMessageId()));
            }
            cabinetService.menuHome(userId);
        } catch (Exception e) {
            log.error("Error handling message for user ID {}: {}", userId, e.getMessage(), e);
            bot.execute(new SendMessage(userId, "ERROR!!!\nXatolik yuz berdi, qayta urinib ko‘ring."));
        }
    }
}