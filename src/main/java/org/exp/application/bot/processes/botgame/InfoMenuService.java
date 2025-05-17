package org.exp.application.bot.processes.botgame;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.message.Translation;
import org.exp.application.models.entity.session.SessionMenu;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramEditService;
import org.exp.application.services.msg.TranslationService;
import org.exp.application.services.session.UserSessionService;
import org.exp.application.utils.Constants;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoMenuService {

    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;
    private final UserSessionService sessionService;
    private final TranslationService translationService;


    public void sendInfoMenu(Long userId){
        UserSession session = sessionService.getOrCreate(userId);
        String menuMsg = translationService.getMessage(Constants.INFO_MENU_MSG, session.getLanguage());
        Translation translation = translationService.getTranslation(SessionMenu.INFO, session.getLanguage());
        String backBtnMsg = translationService.getMessage(Constants.BACK_BTN, session.getLanguage());

        Integer messageId = editService.editMessage(
                userId,
                session.getMessageId(),
                translation.getValue(),
                buttonService.backBtn(backBtnMsg)
        );

        sessionService.updateMessageId(userId, messageId);
    }
}
