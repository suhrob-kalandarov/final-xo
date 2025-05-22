package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.model.request.InlineQueryResultArticle;
import com.pengrad.telegrambot.model.request.InlineQueryResultsButton;
import com.pengrad.telegrambot.model.request.InputTextMessageContent;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import com.pengrad.telegrambot.response.BaseResponse;
import jakarta.transaction.Transactional;
import org.exp.application.bot.processes.multigame.MultiGameService;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.entity.game.MultiGame;
import org.exp.application.services.TelegramButtonService;
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
    private final TgUserService userService;
    private final MultiGameService gameService;
    private final TelegramButtonService buttonService;

    @Transactional
    @Override
    public void handle(InlineQuery inlineQuery) {
        try {
            log.info("inlineQuery {}", inlineQuery);
            Long creatorId = inlineQuery.from().id();
            /// user checking
            TgUser tgUser = userService.getOrCreateTgUser(inlineQuery);
            log.info("user {}", tgUser.toString());
            //String fullName = userService.buildFullNameFromUpdate(inlineQuery);
            MultiGame multiGame = gameService.getOrCreateMultiGame(creatorId);
            log.info("multiGame {}", multiGame.toString());
            multiGame.setPlayerX(tgUser);
            gameService.save(multiGame);
            log.info("updated {}", multiGame);

            InlineQueryResult[] results = new InlineQueryResult[]{
                    new InlineQueryResultArticle("selected_x_" + multiGame.getId(), "🎮START GAME🎮", "x")
                            .inputMessageContent(new InputTextMessageContent("❌ " + tgUser.getFullname() + " 👈 \n⭕ - ?"))
                            .replyMarkup(buttonService.getMultiBoardBtns(multiGame.getId(), new int[3][3]))
            };
            log.info("info {}", Arrays.toString(results));
            BaseResponse response = telegramBot.execute(
                    new AnswerInlineQuery(inlineQuery.id(), results)
            );
            if (!response.isOk()) {
                log.info("error response status: {}", response);
            }
            log.info("response status: {}", response);
        } catch (Exception e) {
            log.info("exception {}", e.toString());
            e.printStackTrace();
        }
    }
}