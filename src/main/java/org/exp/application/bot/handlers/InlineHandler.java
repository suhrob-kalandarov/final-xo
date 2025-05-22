package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.model.request.InlineQueryResultArticle;
import com.pengrad.telegrambot.model.request.InlineQueryResultsButton;
import com.pengrad.telegrambot.model.request.InputTextMessageContent;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import org.exp.application.bot.processes.multigame.MultiGameService;
import org.exp.application.models.entity.game.MultiGame;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.user.TgUserService;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class InlineHandler implements DataHandler<InlineQuery> {

    private final TelegramBot telegramBot;
    private final TgUserService userService;
    private final MultiGameService gameService;
    private final TelegramButtonService buttonService;

    @Override
    public void handle(InlineQuery inlineQuery) {
        try {
            Long creatorId = inlineQuery.from().id();
            /// user checking
            userService.getOrCreateTgUser(inlineQuery);
            String fullName = userService.buildFullNameFromUpdate(inlineQuery);
            MultiGame multiGame = gameService.getOrCreateMultiGame(creatorId);

            InlineQueryResult[] results = new InlineQueryResult[]{
                    new InlineQueryResultArticle("selected_x_" + multiGame.getId(), "🎮START GAME🎮", "x")
                            .inputMessageContent(new InputTextMessageContent("❌ " + fullName + " 👈 \n⭕ - ?"))
                            .replyMarkup(buttonService.getMultiBoardBtns(multiGame.getId(), new int[3][3]))
            };
            telegramBot.execute(
                    new AnswerInlineQuery(inlineQuery.id(), results)
                            .button(new InlineQueryResultsButton("@xoDemoBot", "bot_uri"))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}