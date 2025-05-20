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
            if (multiGame == null) return;

            multiGame.setInlineMessageId(chosenInlineResult.inlineMessageId());
            multiGame.setStatus(GameStatus.PROGRESS);

            TgUser user = tgUserService.getById(chosenInlineResult.from().id());
            multiGame.setPlayerX(user);
            multiGame.setInTurn(Turn.X);

            gameService.save(multiGame);
        } catch (Exception e) {
            log.error("ChosenInlineResult handle error: {}", e.getMessage());
        }
    }
}

/*
@Override
    public void handle(ChosenInlineResult chosenInlineResult) {
        String inlinedMessageId = chosenInlineResult.inlineMessageId();
        String resultId = chosenInlineResult.resultId();
        Pattern pattern = Pattern.compile("selected_[xo]_(\\d+)");
        Matcher matcher = pattern.matcher(resultId);

        if (!matcher.matches()) {
            System.err.println("Not matches :!: " + matcher);
            return;
        }

        Long gameId = Long.parseLong(matcher.group(1));
        Optional<MultiGame> optionalMultiGame = gameService.findById(gameId);

        if (optionalMultiGame.isEmpty()) {
            System.err.println("Not found optionalMultiGame = " + optionalMultiGame);
            return;
        }

        MultiGame multiGame = optionalMultiGame.get();
        multiGame.setInlineMessageId(inlinedMessageId);
        TgUser multiGameUser = tgUserService.getById(chosenInlineResult.from().id());

        if (resultId.startsWith("selected_x")) {
            multiGame.setPlayerX(multiGameUser);
            multiGame.setInTurn(Turn.X);
        }
        gameService.save(multiGame);
    }
 */