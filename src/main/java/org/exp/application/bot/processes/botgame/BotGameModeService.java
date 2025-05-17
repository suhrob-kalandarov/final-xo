package org.exp.application.bot.processes.botgame;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.result.BotGameResult;
import org.exp.application.models.entity.session.SessionMenu;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.services.BotGameResultService;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramEditService;
import org.exp.application.services.TelegramSenderService;
import org.exp.application.services.msg.TranslationService;
import org.exp.application.services.session.UserSessionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.exp.application.utils.Constants.BOT_GAME_STATISTICS_MSG;

@Service
@RequiredArgsConstructor
public class BotGameModeService {

    private final BotGameService botGameService;
    private final SignMenuService signMenuService;
    private final UserSessionService sessionService;
    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;
    private final TranslationService translationService;
    private final BotGameResultService resultService;

    public void sendMenu(Long userId){
        UserSession session = sessionService.getOrCreate(userId);
        Map<String, String> translations = translationService.getTranslationsMap(SessionMenu.BOT_GAME, session.getLanguage());

        if (!botGameService.hasBotGameRow(userId)) {
            signMenuService.sendSignMenu(userId, session);
            return;
        }

        List<BotGameResult> stats = resultService.getResultsByUser(userId);
        String statisticsText = resultService.formatGameStatistics(stats);

        String messageText = translationService.getMessage(BOT_GAME_STATISTICS_MSG, session.getLanguage())
                .formatted(statisticsText);

        Integer messageId = editService.editMessage(
                userId,
                session.getMessageId(),
                messageText,
                (InlineKeyboardMarkup) buttonService.botGameMenuBtns(translations),
                ParseMode.HTML
        );

        sessionService.updateMessageId(userId, messageId);
    }
}
