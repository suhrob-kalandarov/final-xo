package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.model.ChosenInlineResult;
import org.exp.application.bot.processes.multigame.MultiGameService;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.entity.game.MultiGame;
import org.exp.application.models.enums.GameStatus;
import org.exp.application.models.enums.Turn;
import org.exp.application.services.user.TgUserService;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChosenInlineResultHandler implements DataHandler<ChosenInlineResult> {

    private final TgUserService tgUserService;
    private final MultiGameService gameService;

    @Override
    public void handle(ChosenInlineResult chosenInlineResult) {
        String resultId = chosenInlineResult.resultId();

        if (!resultId.startsWith("selected_x_")) return;

        try {
            Long gameId = Long.parseLong(resultId.substring("selected_x_".length()));
            MultiGame multiGame = gameService.findById(gameId).orElse(null);
            if (multiGame == null) {
                log.error("MultiGame not found for ID: {}", gameId);
                return;
            }

            multiGame.setInlineMessageId(chosenInlineResult.inlineMessageId());
            multiGame.setStatus(GameStatus.PROGRESS);

            Optional<TgUser> optionalTgUser = tgUserService.getFindById(chosenInlineResult.from().id());
            TgUser tgUser;
            if (optionalTgUser.isEmpty()) {
                tgUser = tgUserService.getOrCreateTgUserAsync(chosenInlineResult);
                log.info("Created new TgUser with ID: {}", tgUser.getId());
            } else {
                tgUser = optionalTgUser.get();
                log.info("Found TgUser with ID: {}", tgUser.getId());
            }

            if (tgUser == null) {
                log.error("TgUser is null for Telegram ID: {}", chosenInlineResult.from().id());
                return;
            }

            multiGame.setPlayerX(tgUser);
            multiGame.set_active(true);
            multiGame.setInTurn(Turn.X);
            multiGame.setUpdatedAt(LocalDateTime.now());

            MultiGame savedGame = gameService.saveReturn(multiGame);
            log.info("Saved MultiGame ID: {}, PlayerX ID: {}", savedGame.getId(), savedGame.getPlayerX() != null ? savedGame.getPlayerX().getId() : "null");

        } catch (Exception e) {
            log.error("ChosenInlineResult handle error: {}", e.getMessage(), e);
        }
    }
}