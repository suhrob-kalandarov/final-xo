package org.exp.application.bot.processes;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.game.BotGame;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.enums.Difficulty;
import org.exp.application.repositories.BotGameRepository;
import org.exp.application.services.BotGameLogic;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramEditService;
import org.exp.application.services.TelegramSenderService;
import org.exp.application.services.board.BoardBaseLogic;
import org.exp.application.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.exp.application.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class BotGameService {

    private final BotGameLogic botGameLogic;
    private final BoardBaseLogic boardBaseLogic;
    private final BotGameRepository botGameRepository;

    private final TelegramSenderService senderService;
    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;

    public void handleMove(String data){
        try {
            Long gameId = Long.parseLong(data.split("_")[1]);
            int row = Integer.parseInt(data.split("_")[2]);
            int col = Integer.parseInt(data.split("_")[3]);

            Optional<BotGame> optionalBotGame = findById(gameId);

            if (optionalBotGame.isEmpty()) {
                System.err.println("GameCenter.setSignAndSendBoard");
                System.err.println("data = " + data);
                return;
            }
            BotGame botGame = optionalBotGame.get();

            // Return if the selected slot is busy
            if (botGame.getBoard()[row][col] != 0) {
                System.out.println("busy slot | data = " + data);
                return;
            }

            int[][] board = botGame.getBoard(); // BaseGame'dan oladi

            if (boardBaseLogic.isBoardFull(board)) {
                botGame.initializeBoard();
                botGameRepository.save(botGame);
                board = botGame.getBoard(); // qayta yuklash
            }

            if (board[row][col] != 0) return;

            board[row][col] = 1;
            botGame.setBoard(board);
            botGameRepository.save(botGame);

            if (boardBaseLogic.checkWin(board, 1)) {
                //tgUserService.incrementWin(botGame.getPlayer(), botGame.getDifficulty());
                sendResult(botGame, YOU_WON_MSG);
                return;
            }

            if (boardBaseLogic.isBoardFull(board)) {
                //tgUserService.incrementDraw(botGame.getPlayer(), botGame.getDifficulty());
                sendResult(botGame, DRAW_MSG);
                return;
            }

            int[] botMove = botGameLogic.findBestMove(board, botGame.getDifficulty().name());
            board[botMove[0]][botMove[1]] = 2;
            botGame.setBoard(board);
            botGameRepository.save(botGame);

            if (boardBaseLogic.checkWin(board, 2)) {
                //tgUserService.incrementLose(botGame.getPlayer(), botGame.getDifficulty());
                sendResult(botGame, YOU_LOST_MSG);
                return;
            }

            if (boardBaseLogic.isBoardFull(board)) {
                //tgUserService.incrementDraw(botGame.getPlayer(), botGame.getDifficulty());
                sendResult(botGame, DRAW_MSG);
                return;
            }

            updateGameBoard(botGame);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateGameBoard(BotGame botGame) {
        String playerSign = botGame.getPlayerSymbol();
        String botSign = playerSign.equals(Constants.X_SIGN) ? Constants.O_SIGN : Constants.X_SIGN;

        /*Integer messageId = senderService.sendMessage(
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
                buttonService.getBoardBtns(
                        botGame.getId(),
                        botGame.getBoard()
                )
        );

        botGame.setMessageId(editMessageId);
        save(botGame);
    }

    private void sendResult(BotGame botGame, String resultMessage) {
        TgUser player = botGame.getPlayer();
        int[][] board = botGame.getBoard();

        senderService.execute(getResultMessageText(botGame, resultMessage, formatBoard(botGame)));

        int newMessageId = senderService.execute(
                new SendMessage(player.getId(), USER_STATISTICS_MSG)
                        .parseMode(ParseMode.HTML)
                        .replyMarkup(buttonService.botGameMenuBtns(botGame.getId()))
        );

        botGame.setMessageId(newMessageId);
        botGame.initializeBoard();
        botGameRepository.save(botGame);
    }

    private EditMessageText getResultMessageText(BotGame botGame, String resultMessage, String boardState) {
        return new EditMessageText(
                botGame.getPlayer().getId(),
                botGame.getMessageId(),
                RESULT_MSG.formatted(resultMessage, botGame.getDifficulty(), "Board:")  + boardState
        ).parseMode(ParseMode.HTML);
    }

    private String formatBoard(BotGame botGame) {

        String botSign = botGame.getPlayerSymbol().equals(Constants.X_SIGN) ? Constants.O_SIGN : Constants.X_SIGN;

        int[][] board = botGame.getBoard();
        StringBuilder sb = new StringBuilder();
        String padding = "  ";
        sb.append("<pre>");
        for (int[] row : board) {
            sb.append(padding);
            for (int cell : row) {
                String symbol = switch (cell) {
                    case 1 -> botGame.getPlayerSymbol();
                    case 2 -> botSign;
                    default -> EMPTY_SIGN;
                };
                sb.append(symbol).append(padding);
            }
            sb.append("\n");
        }
        sb.append("</pre>");
        return sb.toString();
    }

    private String getBotSymbol() {
        // Agar har bir userga xos bo‘lsa, DB dan olinadi, aks holda umumiy belgi
        return "⭕";
    }

    public BotGame getOrCreateBotGame(TgUser player, Integer messageId){
        Optional<BotGame> optionalBotGame = getByPlayerId(player.getId());

        if (optionalBotGame.isPresent()) {
            BotGame botGame = optionalBotGame.get();
            botGame.setMessageId(messageId);
            botGame.initializeBoard();
            return botGame;
        }

        return createReturnBotGame(player, messageId);
    }

    public BotGame getOrCreateBotGame(TgUser player){
        Optional<BotGame> optionalBotGame = getByPlayerId(player.getId());

        if (optionalBotGame.isPresent()) {
            BotGame botGame = optionalBotGame.get();
            botGame.initializeBoard();
            return botGame;
        }

        return createReturnBotGame(player);
    }


    public BotGame createReturnBotGame(TgUser player, Integer messageId) {
        BotGame newBotGame = BotGame.builder()
                .difficulty(Difficulty.EASY)
                .messageId(messageId)
                .player(player)
                .build();
        return botGameRepository.save(newBotGame);
    }

    public BotGame createReturnBotGame(TgUser player) {
        BotGame newBotGame = BotGame.builder()
                .difficulty(Difficulty.EASY)
                .player(player)
                .build();
        return botGameRepository.save(newBotGame);
    }


    public Optional<BotGame> getByPlayerId(Long id) {
        return botGameRepository.findByPlayerId(id);
    }

    public void save(BotGame botGame) {
        botGameRepository.save(botGame);
    }

    public BotGame saveReturn(BotGame botGame) {
        return botGameRepository.save(botGame);
    }

    public Optional<BotGame> findById(Long number) {
        return botGameRepository.findById(number);
    }
}
