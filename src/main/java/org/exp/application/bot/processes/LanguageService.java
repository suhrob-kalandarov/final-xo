package org.exp.application.bot.processes;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.entity.extra.BotLanguage;
import org.exp.application.repositories.BotLanguageRepository;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramSenderService;
import org.exp.application.services.main.TgUserService;
import org.exp.application.utils.SessionBuffer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final TelegramSenderService senderService;
    private final TelegramButtonService buttonService;
    private final TgUserService tgUserService;
    private final BotLanguageRepository languageRepository;

    public void sendLangMenu(Long userId) {
        senderService.sendMessage(userId, "Choose bot language:", buttonService.langMenuBtns());
    }

    public void setBotLanguage(Long userId, String data) {
        Long langId = Long.parseLong(data.split("_")[1]);

        BotLanguage botLanguage = getById(langId);

        TgUser tgUser = SessionBuffer.sessionTgUsers.get(userId);
        tgUser.setLanguage(botLanguage);
        tgUserService.save(tgUser);

        System.out.println("Success set lang!");
    }

    public BotLanguage getById(Long langId) {
        return languageRepository.findById(langId)
                .orElseThrow(() -> new RuntimeException("Language not found!"));
    }
}