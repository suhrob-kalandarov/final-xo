package org.exp.application.bot.processes.botgame;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
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
import org.exp.application.services.main.TgUserService;
import org.exp.application.services.session.UserSessionService;
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

    private final TgUserService tgUserService;

    private final UserSessionService sessionService;

    private final TelegramSenderService senderService;
    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;

    public void handleMove(String data){
        try {
            String payload = data.substring("game-cell_".length());
            String[] parts = payload.split("_");

            Long gameId = Long.parseLong(parts[0]);
            int row = Integer.parseInt(parts[1]);
            int col = Integer.parseInt(parts[2]);

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

            int[][] board = botGame.getBoard();

            if (boardBaseLogic.isBoardFull(board)) {
                botGame.initializeBoard();
                botGameRepository.save(botGame);
                board = botGame.getBoard();
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

            int[] botMove = botGameLogic.findBestMove(board, botGame.getDifficulty());
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
        String playerSign = botGame.getPlayerSign();
        String botSign = getBotSymbol(playerSign);

        Integer editMessageId = editService.editMessage(
                botGame.getPlayer().getId(),
                botGame.getMessageId(),
                "🎮 GAME BOARD" +
                        "\nYou: " + playerSign +
                        "\nBot: " + botSign,
                buttonService.getBoardBtns(
                        botGame.getId(),
                        botGame.getBoard(),
                        botGame.getPlayerSign()
                )
        );

        botGame.setMessageId(editMessageId);
        save(botGame);
    }

    private void sendResult(BotGame botGame, String resultMessage) {
        TgUser player = botGame.getPlayer();
        int[][] board = botGame.getBoard();

        senderService.execute(getResultMessageText(botGame, resultMessage, formatBoard(botGame.getPlayerSign(), board)));

        sendMenu(botGame);
    }

    public void sendMenu(BotGame botGame){
        int newMessageId = senderService.execute(
                new SendMessage(
                        botGame.getPlayer().getId(),
                        getStatMessage()
                )
                        .parseMode(ParseMode.HTML)
                        .replyMarkup(buttonService.botGameMenuBtns())
        );

        sessionService.updateMessageId(botGame.getPlayer().getId(), newMessageId);
        botGame.initializeBoard();
        save(botGame);
    }

    public void editAndSendMenu(BotGame botGame, Integer messageId){
        Long userId = botGame.getPlayer().getId();
        int editMessageId = editService.editMessage(
                userId,
                messageId,
                getStatMessage(),
                (InlineKeyboardMarkup) buttonService.botGameMenuBtns(),
                ParseMode.HTML
        );

        botGame.setMessageId(editMessageId);
        botGame.initializeBoard();
        save(botGame);
    }

    public String getStatMessage() {
        return USER_STATISTICS_MSG.formatted(
                """
                        \s\s\s\s\s🏆   ⚖️   😭
                        EA👶
                        ME😎
                        HA😈
                        EE🥶
                        """
        );
    }


    private EditMessageText getResultMessageText(BotGame botGame, String resultMessage, String boardState) {

        return new EditMessageText(
                botGame.getPlayer().getId(),
                botGame.getMessageId(),
                RESULT_MSG.formatted(resultMessage, botGame.getDifficulty(), "Board:")  + boardState
        ).parseMode(ParseMode.HTML);
    }

    private String formatBoard(String playerSign, int[][] board) {
        String botSign = getBotSymbol(playerSign);
        StringBuilder sb = new StringBuilder();
        String padding = "  ";
        sb.append("<pre>");
        for (int[] row : board) {
            sb.append(padding);
            for (int cell : row) {
                String symbol = switch (cell) {
                    case 1 -> playerSign;
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

    private String getBotSymbol(String playerSign) {
        return playerSign.equals(Constants.X_SIGN) ? Constants.O_SIGN : Constants.X_SIGN;
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

    public Optional<BotGame> findById(Long userId) {
        return botGameRepository.findById(userId);
    }

    public Optional<BotGame> findByPlayerId(Long userId) {
        return botGameRepository.findByPlayerId(userId);
    }

    public boolean hasBotGameRow(Long playerId) {
        return botGameRepository.existsByPlayer_Id(playerId);
    }
}
