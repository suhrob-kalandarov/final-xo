package org.exp.application.bot.processes.botgame;

import com.pengrad.telegrambot.model.request.ParseMode;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.models.enums.Difficulty;
import org.exp.application.repositories.BotGameRepository;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramEditService;
import org.exp.application.services.session.UserSessionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotGameDifficultyService {

    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;

    private final UserSessionService sessionService;
    private final BotGameModeService botGameModeService;

    private final BotGameRepository botGameRepository;

    public void sendMenu(Long userId) {
        UserSession session = sessionService.getOrCreate(userId);

        Integer messageId = editService.editMessage(userId, session.getMessageId(),
                "DIFFICULTY MENU",
                buttonService.difficultyMenuButtons(),
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
