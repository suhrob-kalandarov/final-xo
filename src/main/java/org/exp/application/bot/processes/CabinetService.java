package org.exp.application.bot.processes;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramEditService;
import org.exp.application.services.TelegramSenderService;
import org.exp.application.services.main.TgUserService;
import org.exp.application.services.session.UserSessionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CabinetService {

    private final TgUserService tgUserService;
    private final TelegramSenderService senderService;
    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;

    private final UserSessionService sessionService;

    public void menuHome(Long userId) {
        //UserSession session = sessionService.getOrCreate(userId);
        Integer messageId = senderService.sendMessage(
                userId,
                "MAIN_MENU (HOME)",
                buttonService.homeMenuBtns()
        );
        sessionService.updateMessageId(userId, messageId);
    }

    public void editSendHome(Long userId) {
        UserSession session = sessionService.getOrCreate(userId);
        Integer messageId = editService.editMessage(
                userId,
                session.getMessageId(),
                "MAIN_MENU (HOME)",
                (InlineKeyboardMarkup) buttonService.homeMenuBtns()
        );
        sessionService.updateMessageId(userId, messageId);
    }
}
