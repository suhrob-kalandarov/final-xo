package org.exp.application.bot.processes;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.entity.session.SessionMenu;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramEditService;
import org.exp.application.services.TelegramSenderService;
import org.exp.application.services.msg.TranslationService;
import org.exp.application.services.session.UserSessionService;
import org.exp.application.services.user.TgUserService;
import org.exp.application.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CabinetService {

    private final TelegramSenderService senderService;
    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;

    private final UserSessionService sessionService;
    private final TgUserService tgUserService;
    private final TranslationService translationService;

    public void menuHome(Long userId) {
        UserSession session = sessionService.getOrCreate(userId);
        TgUser tgUser = tgUserService.getById(userId);
        Map<String, String> messages = translationService.getTranslationsMap(SessionMenu.HOME, session.getLanguage());

        Integer messageId = senderService.sendMessage(
                userId,
                messages.get(Constants.HOME_MSG).formatted(tgUser.getFullname()),
                buttonService.homeMenuBtns(messages)
        );
        sessionService.updateMessageId(userId, messageId);
    }

    public void editSendHome(Long userId) {
        UserSession session = sessionService.getOrCreate(userId);
        TgUser tgUser = tgUserService.getById(userId);
        Map<String, String> messages = translationService.getTranslationsMap(SessionMenu.HOME, session.getLanguage());

        Integer messageId = editService.editMessage(
                userId,
                session.getMessageId(),
                messages.get(Constants.HOME_MSG).formatted(tgUser.getFullname()),
                (InlineKeyboardMarkup) buttonService.homeMenuBtns(messages)
        );
        sessionService.updateMessageId(userId, messageId);
    }
}
