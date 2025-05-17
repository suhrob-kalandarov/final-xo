package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.model.ChosenInlineResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.application.bot.processes.multigame.MultiGameService;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.entity.game.MultiGame;
import org.exp.application.models.enums.Turn;
import org.exp.application.services.user.TgUserService;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChosenInlineResultHandler implements DataHandler<ChosenInlineResult> {

    private final TgUserService tgUserService;
    private final MultiGameService gameService;

    @Override
    public void handle(ChosenInlineResult chosenInlineResult) {
        System.err.println("ChosenInlineResultHandler.handle" + chosenInlineResult.toString());

        String inlinedMessageId = chosenInlineResult.inlineMessageId();
        String resultId = chosenInlineResult.resultId();

        Pattern pattern = Pattern.compile("selected_[xo]_(\\d+)");
        System.err.println("pattern = " + pattern);

        Matcher matcher = pattern.matcher(resultId);
        System.err.println("matcher = " + matcher);

        if (!matcher.matches()) {
            System.err.println("Not matches :!: " + matcher);
            return;
        }

        Long gameId = Long.parseLong(matcher.group(1));
        System.err.println("gameId = " + gameId);

        Optional<MultiGame> optionalMultiGame = gameService.findById(gameId);
        System.err.println("optionalMultiGame = " + optionalMultiGame);

        if (optionalMultiGame.isEmpty()) {
            System.err.println("Not found optionalMultiGame = " + optionalMultiGame);
            return;
        }

        MultiGame multiGame = optionalMultiGame.get();
        System.err.println("multiGame = " + multiGame);

        multiGame.setInlineMessageId(inlinedMessageId);
        System.err.println("inlinedMessageId = " + inlinedMessageId);
        System.err.println("multiGame.getInlineMessageId() = " + multiGame.getInlineMessageId());

        TgUser multiGameUser = tgUserService.getById(chosenInlineResult.from().id());

        if (resultId.startsWith("selected_x")) {
            System.err.println("resultId = " + resultId);
            System.err.println("multiGame.getPlayerX() = " + multiGame.getPlayerX());

            multiGame.setPlayerX(multiGameUser);

            System.err.println("multiGame.getPlayerX() = " + multiGame.getPlayerX());
            System.err.println("multiGame.getInTurn() = " + multiGame.getInTurn());

            multiGame.setInTurn(Turn.X);

            System.err.println("multiGame.getInTurn() = " + multiGame.getInTurn());
        }
        MultiGame savedMultiGame = gameService.saveReturn(multiGame);

        System.err.println("savedMultiGame = " + savedMultiGame);
    }
}