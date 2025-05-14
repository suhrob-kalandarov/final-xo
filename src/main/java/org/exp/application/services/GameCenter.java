package org.exp.application.services;

import lombok.RequiredArgsConstructor;
import org.exp.application.bot.processes.BotGameService;
import org.exp.application.models.entity.game.BotGame;
import org.exp.application.models.enums.GameStatus;
import org.exp.application.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameCenter {

    private final BotGameService botGameService;
    private final TelegramSenderService senderService;
    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;

    public void setSignAndSendBoard(String data) {
        String[] parts = data.split("-|_");
        String sign = parts[1];
        Long gameId = Long.parseLong(parts[2]);

        Optional<BotGame> optionalBotGame = botGameService.findById(gameId);

        if (optionalBotGame.isEmpty()) {
            System.err.println("GameCenter.setSignAndSendBoard");
            System.err.println("data = " + data);
            return;
        }

        String trueGameSign = getTrueGameSign(sign);

        BotGame botGame = optionalBotGame.get();
        botGame.set_active(true);
        botGame.setPlayerSymbol(trueGameSign);
        botGame.setStatus(GameStatus.IN_GAME);
        botGame = botGameService.saveReturn(botGame);

        sendBoard(botGame);
    }

    public void sendBoard(BotGame botGame) {
        String playerSign = botGame.getPlayerSymbol();
        String botSign = playerSign.equals(Constants.X_SIGN) ? Constants.O_SIGN : Constants.X_SIGN;

        /*senderService.sendMessage(
                botGame.getPlayer().getId(),
                "🎮 GAME BOARD" +
                        "\nYou: " + playerSign +
                        "\nBot: " + botSign,
                buttonService.getBoardBtns(botGame.getId(), botGame.getBoard())
        );*/

        Integer editMessageId = editService.editMessage(
                botGame.getPlayer().getId(),
                botGame.getMessageId(),
                "🎮 GAME BOARD" +
                        "\nYou: " + playerSign +
                        "\nBot: " + botSign,
                buttonService.getBoardBtns(botGame.getId(), botGame.getBoard())
        );
        botGame.setMessageId(editMessageId);
        botGameService.save(botGame);
    }


    private String getTrueGameSign(String sign) {
        if (sign.equals("o")) return Constants.O_SIGN;
        return Constants.X_SIGN;
    }
}
