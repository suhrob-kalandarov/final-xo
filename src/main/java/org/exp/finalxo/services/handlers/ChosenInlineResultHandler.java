package org.exp.finalxo.services.handlers;

import com.pengrad.telegrambot.model.ChosenInlineResult;
import lombok.RequiredArgsConstructor;
import org.exp.xo3bot.dtos.MainDto;
import org.exp.xo3bot.entity.multigame.MultiGame;
import org.exp.xo3bot.entity.multigame.MultiGameUser;
import org.exp.xo3bot.entity.multigame.Turn;
import org.exp.xo3bot.services.multigame.MultiGameUserService;
import org.exp.xo3bot.usekeys.Handler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ChosenInlineResultHandler implements Handler<ChosenInlineResult> {

    private final MainDto dto;
    private final MultiGameUserService multiGameUserService;

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

        Optional<MultiGame> optionalMultiGame = dto.getMultiGameRepository().findById(gameId);
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

        MultiGameUser multiGameUser = multiGameUserService.getOrCreatePlayer(chosenInlineResult);

        if (resultId.startsWith("selected_x")) {
            System.err.println("resultId = " + resultId);

            /// Agar Player O allaqachon shu odam bo'lsa, ruxsat bermaslik
            /*if (multiGame.getPlayerO() != null && userId.equals(multiGame.getPlayerO().getId())) {

                dto.getTelegramBot().execute(
                        new AnswerCallbackQuery(inlinedMessageId).text("User(" + userId + ") already joined as O !")
                );

                System.out.println("User(" + userId + ") already joined as O");
                return;
            }*/


            System.err.println("multiGame.getPlayerX() = " + multiGame.getPlayerX());
            multiGame.setPlayerX(multiGameUser);
            System.err.println("multiGame.getPlayerX() = " + multiGame.getPlayerX());

            System.err.println("multiGame.getInTurn() = " + multiGame.getInTurn());
            multiGame.setInTurn(Turn.PLAYER_X);
            System.err.println("multiGame.getInTurn() = " + multiGame.getInTurn());

        }
        System.err.println("multiGame = " + multiGame);
        MultiGame savedMultiGame = dto.getMultiGameRepository().save(multiGame);
        System.err.println("savedMultiGame = " + savedMultiGame);
    }
}


/* in a future version of bot
     else if (resultId.startsWith("selected_o")) {

            /// Agar Player X shu odam bo'lsa, ruxsat bermaslik
            if (multiGame.getPlayerX() != null && userId.equals(multiGame.getPlayerX().getId())) {

                dto.getTelegramBot().execute(
                        new AnswerCallbackQuery(inlinedMessageId).text("User already joined as X !")
                );

                System.out.println("User already joined as X");
                return;
            }
            multiGame.setPlayerO(multiGameUser);
            multiGame.setInTurn(Turn.PLAYER_X);
        }
*/