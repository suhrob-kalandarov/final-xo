package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.model.request.InlineQueryResultArticle;
import com.pengrad.telegrambot.model.request.InlineQueryResultsButton;
import com.pengrad.telegrambot.model.request.InputTextMessageContent;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import com.pengrad.telegrambot.response.BaseResponse;
import org.exp.application.bot.processes.multigame.MultiGameService;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.entity.game.MultiGame;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.session.UserSessionService;
import org.exp.application.services.user.TgUserService;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class InlineHandler implements DataHandler<InlineQuery> {

    private final TelegramBot telegramBot;
    private final TelegramButtonService buttonService;

    private final TgUserService userService;
    private final MultiGameService gameService;


    @Override
    public void handle(InlineQuery inlineQuery) {
        System.err.println("InlineHandler.handle " + inlineQuery.toString());
        try {
            Long creatorId = inlineQuery.from().id();
            TgUser user = userService.getOrCreateTgUser(inlineQuery);
            String fullName = userService.buildFullNameFromUpdate(inlineQuery);

            MultiGame multiGame = gameService.getOrCreateMultiGame(creatorId);
            System.err.println("inlineQuery.id() = " + inlineQuery.id());
            System.err.println("multiGame = " + multiGame);

            MultiGame savedMultiGame = gameService.saveReturn(multiGame);
            System.err.println("saved multigame = " + savedMultiGame);

            InlineQueryResult[] results = new InlineQueryResult[]{
                    new InlineQueryResultArticle("selected_x_" + multiGame.getId(), "❌ START GAME ⭕", "x")
                            .inputMessageContent(new InputTextMessageContent("❌ " + fullName + " 👈 \n⭕ - ?"))
                            .replyMarkup(buttonService.getMultiBoardBtns(savedMultiGame.getId(), new int[3][3]))
            };

            System.err.println("inline query results = " + Arrays.toString(results));

            BaseResponse baseResponse = telegramBot.execute(new AnswerInlineQuery(inlineQuery.id(), results)
                    .button(
                            new InlineQueryResultsButton("@xoDemoBot", "bot_uri")
                    )
            );

            System.err.println("baseResponse.isOk() = " + baseResponse.isOk());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}