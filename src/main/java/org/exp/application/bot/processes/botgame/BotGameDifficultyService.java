package org.exp.application.bot.processes.botgame;

import com.pengrad.telegrambot.model.request.ParseMode;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.session.SessionMenu;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.models.enums.Difficulty;
import org.exp.application.repositories.botgame.BotGameRepository;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramEditService;
import org.exp.application.services.msg.TranslationService;
import org.exp.application.services.session.UserSessionService;
import org.exp.application.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BotGameDifficultyService {

    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;

    private final UserSessionService sessionService;
    private final BotGameModeService botGameModeService;

    private final BotGameRepository botGameRepository;

    private final TranslationService translationService;

    public void sendMenu(Long userId) {
        UserSession session = sessionService.getOrCreate(userId);
        Map<String, String> translations = translationService.getTranslationsMap(SessionMenu.DIFFICULTY, session.getLanguage());
        Integer messageId = editService.editMessage(userId, session.getMessageId(),
                translations.get(Constants.DIFFICULTY_LEVEL_MSG),
                buttonService.difficultyMenuButtons(translations),
                ParseMode.HTML
        );

        sessionService.updateMessageId(userId, messageId);
    }

    public void setDifficulty(Long userId, String data) {
        String payload = data.substring("game-level_".length());

        // Enumga aylantiramiz
        Difficulty difficulty = Difficulty.valueOf(payload);

        botGameRepository.updateDifficultyByUserId(userId, difficulty);

        botGameModeService.sendMenu(userId);
    }
}
