package org.exp.application.bot.processes.botgame;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramEditService;
import org.exp.application.services.session.UserSessionService;
import org.springframework.stereotype.Service;

import static org.exp.application.utils.Constants.USER_STATISTICS_MSG;

@Service
@RequiredArgsConstructor
public class BotGameModeService {

    private final BotGameService botGameService;
    private final SignMenuService signMenuService;
    private final UserSessionService sessionService;

    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;


    public void sendMenu(Long userId){
        UserSession session = sessionService.getOrCreate(userId);

        if (!botGameService.hasBotGameRow(userId)) {
            signMenuService.sendSignMenu(userId, session);
            return;
        }

        Integer messageId = editService.editMessage(
                userId, session.getMessageId(),
                getStatMessage(),
                (InlineKeyboardMarkup) buttonService.botGameMenuBtns(),
                ParseMode.HTML
        );

        sessionService.updateMessageId(userId, messageId);
    }


    public String getStatMessage() {
        return USER_STATISTICS_MSG.formatted(
                """
                        \s\s\s\s\s🏆   ⚖️   😭
                        EA👶
                        ME😎
                        HA😈
                        EE🥶
                        """
        );
    }
}
