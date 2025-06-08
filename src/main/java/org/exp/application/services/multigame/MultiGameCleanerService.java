package org.exp.application.services.multigame;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.game.MultiGame;
import org.exp.application.models.enums.GameStatus;
import org.exp.application.repositories.multigame.MultiGameRepository;
import org.exp.application.services.TelegramButtonService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MultiGameCleanerService {

    private final TelegramBot telegramBot;
    private final MultiGameRepository gameRepository;
    private final TelegramButtonService buttonService;

    @Scheduled(fixedDelay = 50_000)
    public void cleanExpiredGames() {
        System.err.println("MultiGameCleanerService.cleanExpiredGames");
        List<MultiGame> games = gameRepository.findAllByStatusIn(
                List.of(GameStatus.CREATED, GameStatus.IN_GAME, GameStatus.PROGRESS)
        );

        LocalDateTime now = LocalDateTime.now();

        for (MultiGame game : games) {
            if (game.getUpdatedAt() != null && Duration.between(game.getUpdatedAt(), now).toMinutes() >= 5) {
                try {
                    EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup(
                            game.getInlineMessageId()
                    ).replyMarkup(buttonService.endMultiGameBtns());
                    telegramBot.execute(editMarkup);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                cleanAndUpdate(game);
            }
        }
    }

    public void cleanAndUpdate(MultiGame game) {
        game.setCreatorId(null);
        game.setPlayerO(null);
        game.setPlayerX(null);
        game.setWinnerId(null);
        game.setInTurn(null);
        game.setInlineMessageId(null);
        game.set_active(false);
        game.setMoveCount(null);
        game.setEndedAt(null);
        game.setStatus(GameStatus.IDLE);
        game.initializeBoard();

        gameRepository.save(game);
    }
}
