package org.exp.application.bot.processes.botgame;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.entity.game.BotGame;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.models.enums.GameStatus;
import org.exp.application.services.BotGameLogic;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramEditService;
import org.exp.application.services.main.TgUserService;
import org.exp.application.services.session.UserSessionService;
import org.exp.application.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignMenuService {

    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;

    private final TgUserService tgUserService;
    private final BotGameService botGameService;
    private final BotGameLogic botGameLogic;
    private final UserSessionService sessionService;

    public void sendSignMenu(Long userId, UserSession session) {  /// opt !?
        TgUser tgUser = tgUserService.getById(userId);
        BotGame botGame = botGameService.getOrCreateBotGame(tgUser);

        Integer editMessageId = editService.editMessage(
                tgUser.getId(), session.getMessageId(),
                "CHOOSE_SIGN_MENU",
                (InlineKeyboardMarkup) buttonService.menuChooseXO(botGame)
        );

        sessionService.updateMessageId(userId, editMessageId);
    }

    public void sendSignMenu(Long userId) {  /// opt !?
        UserSession session = sessionService.getOrCreate(userId);
        TgUser tgUser = tgUserService.getById(userId);
        BotGame botGame = botGameService.getOrCreateBotGame(tgUser);

        Integer editMessageId = editService.editMessage(
                tgUser.getId(), session.getMessageId(),
                "CHOOSE_SIGN_MENU",
                (InlineKeyboardMarkup) buttonService.menuChooseXO(botGame)
        );

        sessionService.updateMessageId(userId, editMessageId);
    }

    public void setSignAndSendBoard(String data) {
        String payload = data.substring("game-player-sign-".length());

        String[] parts = payload.split("_");
        String sign = parts[0];
        Long gameId = Long.parseLong(parts[1]);

        Optional<BotGame> optionalBotGame = botGameService.findById(gameId);

        if (optionalBotGame.isEmpty()) {
            System.err.println("GameCenter.setSignAndSendBoard");
            System.err.println("data = " + data);
            return;
        }

        String trueGameSign = getTrueGameSign(sign);

        BotGame botGame = optionalBotGame.get();
        botGame.set_active(true);
        botGame.setPlayerSign(trueGameSign);
        botGame.setStatus(GameStatus.IN_GAME);
        botGame = botGameService.saveReturn(botGame);

        sendBoard(botGame);
    }

    public void sendBoard(BotGame botGame) {
        UserSession session = sessionService.getOrCreate(botGame.getPlayer().getId());
        String playerSign = botGame.getPlayerSign();
        String botSign = playerSign.equals(Constants.X_SIGN) ? Constants.O_SIGN : Constants.X_SIGN;

        if (playerSign.equals(Constants.O_SIGN)){
            int[] botMove = botGameLogic.findBestMove(botGame.getBoard(), botGame.getDifficulty());
            int[][] board = botGame.getBoard();
            board[botMove[0]][botMove[1]] = 2;
            botGame.setBoard(board);
        }

        Integer editMessageId = editService.editMessage(
                botGame.getPlayer().getId(),
                session.getMessageId(),
                "🎮 GAME BOARD" +
                        "\nYou: " + playerSign +
                        "\nBot: " + botSign,
                buttonService.getBoardBtns(botGame.getId(), botGame.getBoard(), botGame.getPlayerSign())
        );
        botGame.setMessageId(editMessageId);
        botGameService.save(botGame);
    }

    private String getTrueGameSign(String sign) {
        if (sign.equals("o")) return Constants.O_SIGN;
        return Constants.X_SIGN;
    }
}
