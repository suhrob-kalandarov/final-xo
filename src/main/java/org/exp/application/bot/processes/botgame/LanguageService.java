package org.exp.application.bot.processes.botgame;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.RequiredArgsConstructor;
import org.exp.application.bot.processes.CabinetService;
import org.exp.application.models.entity.message.Language;
import org.exp.application.models.entity.message.Translation;
import org.exp.application.models.entity.session.SessionMenu;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.repositories.common.LanguageRepository;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramEditService;
import org.exp.application.services.msg.TranslationService;
import org.exp.application.services.session.UserSessionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;
    private final LanguageRepository languageRepository;
    private final CabinetService cabinetService;
    private final UserSessionService sessionService;
    private final TranslationService translationService;

    public void sendLangMenu(Long userId) {
        UserSession session = sessionService.getOrCreate(userId);
        Translation translation = translationService.getTranslation(SessionMenu.LANGUAGE, session.getLanguage());
        Integer messageId = editService.editMessage(
                userId, session.getMessageId(),
                translation.getValue(),
                (InlineKeyboardMarkup) buttonService.langMenuBtns()
        );
        sessionService.updateMessageId(userId, messageId);
    }

    public void setBotLanguage(Long userId, String data) {
        Long langId = Long.parseLong(data.split("_")[1]);
        Language botLanguage = getById(langId);
        sessionService.updateLanguage(userId, botLanguage);
        cabinetService.editSendHome(userId);
    }

    public Language getById(Long langId) {
        return languageRepository.findById(langId)
                .orElseThrow(() -> new RuntimeException("Language not found!"));
    }
}