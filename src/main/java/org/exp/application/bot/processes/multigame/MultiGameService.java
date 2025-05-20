package org.exp.application.bot.processes.multigame;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.EditMessageText;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.entity.game.MultiGame;
import org.exp.application.models.enums.GameStatus;
import org.exp.application.repositories.multigame.MultiGameRepository;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.multigame.MultiGameLogicService;
import org.exp.application.services.user.TgUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MultiGameService {

    private final TelegramBot telegramBot;
    private final TelegramButtonService buttonService;

    private final TgUserService userService;
    private final MultiGameRepository gameRepository;
    private final MultiGameLogicService logicService;

    public void execute(CallbackQuery callbackQuery) {
        String data = callbackQuery.data();

        String[] parts = data.split("_");
        Long gameId = Long.parseLong(parts[1]);
        int row = Integer.parseInt(parts[2]);
        int col = Integer.parseInt(parts[3]);

        Optional<MultiGame> optionalMultiGame = findById(gameId);

        if (optionalMultiGame.isEmpty()) {
            telegramBot.execute(
                    new AnswerCallbackQuery(callbackQuery.id()).text("Game not exist!").showAlert(true)
            );
            return;
        }

        MultiGame multiGame = optionalMultiGame.get();

        if (multiGame.getPlayerX() == null || multiGame.getPlayerO() == null) {
            multiGame = gamePlayersInfoFiller(multiGame, callbackQuery);
        }

        if (data.startsWith("MOVE_")) {
            logicService.handleMove(gameId, row, col, callbackQuery);
        }
    }


    public MultiGame gamePlayersInfoFiller(MultiGame multiGame, CallbackQuery callbackQuery) {
        Long userId = callbackQuery.from().id();
        TgUser player = userService.getOrCreateTgUser(callbackQuery);

        if (multiGame.getPlayerO() == null) {

            if (!multiGame.getPlayerX().getId().equals(userId)){
                multiGame.setPlayerO(player);

                telegramBot.execute(
                        new EditMessageText(multiGame.getInlineMessageId(),
                                "❌" + multiGame.getPlayerX().getFullname()
                                        +
                                        " 👈\n⭕" + multiGame.getPlayerO().getFullname()
                        ).replyMarkup(buttonService.getMultiBoardBtns(multiGame.getId(), multiGame.getBoard()))
                );

                save(multiGame);

            } else {
                System.err.println("not your turn" + player.getId() + " " + player.getFullname());
                return null;
            }
        }

        return saveReturn(multiGame);
    }

    public MultiGame getOrCreateMultiGame(Long creatorId) {
        Optional<MultiGame> multiGameOptional = gameRepository.findFirstByStatus(GameStatus.IDLE);
        MultiGame multiGame;

        if (multiGameOptional.isPresent()) {
            multiGame = multiGameOptional.get();
            multiGame.setStatus(GameStatus.PROGRESS);
            return multiGame;

        } else {
            multiGame = MultiGame.builder()
                    .creatorId(creatorId)
                    .status(GameStatus.CREATED)
                    .board(new int[3][3])
                    .build();
        }
        return saveReturn(multiGame);
    }

    public Optional<MultiGame> findById(Long gameId) {
        return gameRepository.findById(gameId);
    }

    public void save(MultiGame game) {
        gameRepository.save(game);
    }

    public MultiGame saveReturn(MultiGame game) {
        return gameRepository.save(game);
    }
}
