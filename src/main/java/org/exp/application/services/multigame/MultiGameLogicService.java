package org.exp.application.services.multigame;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.EditMessageText;
import org.exp.application.models.entity.game.MultiGame;
import org.exp.application.models.enums.GameStatus;
import org.exp.application.models.enums.Turn;
import org.exp.application.repositories.multigame.MultiGameRepository;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.board.BoardBaseLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MultiGameLogicService {

    private final TelegramBot telegramBot;
    private final TelegramButtonService buttonService;
    private final MultiGameRepository gameRepository;
    private final BoardBaseLogic baseLogic;
    private final MultiGameCleanerService boardCleaner;

    public void handleMove(Long gameId, int row, int col, CallbackQuery callbackQuery) {
        Long userId = callbackQuery.from().id();
        Optional<MultiGame> optionalGame = gameRepository.findById(gameId);

        if (optionalGame.isEmpty()) {
            telegramBot.execute(
                    new AnswerCallbackQuery(callbackQuery.id()).text("Game not found!").showAlert(true)
            );
            return;
        }

        MultiGame game = optionalGame.get();
        int[][] board = game.getBoard();
        String inlineMessageId = game.getInlineMessageId();

        if (inlineMessageId == null) {
            telegramBot.execute(
                    new AnswerCallbackQuery(callbackQuery.id()).text("❌ inlineMessageId is NULL for gameId = " + gameId).showAlert(true)
            );
            return;
        }

        // 🔒 Qo‘shimcha himoya: PlayerO hali yo‘q va PlayerX o‘zi yana bosmoqda
        if (game.getPlayerO() == null && userId.equals(game.getPlayerX().getId())) {
            telegramBot.execute(new AnswerCallbackQuery(callbackQuery.id())
                    .text("Game is not ready yet! \nWaiting for second player...")
                    .showAlert(true));
            return;
        }

        /*// 1. O'yinchi tekshiruvi (gamega tegishliligi)
        if (game.getPlayerX() == null || game.getPlayerO() == null) {
            telegramBot.execute(new AnswerCallbackQuery(callbackQuery.id())
                    .text("Game is not ready yet! \nWaiting for second player...")
                    .showAlert(true));
            return;
        }*/

        // 2. O'yinchi bu o'yinda o'ynayotganligini tekshirish
        if (!userId.equals(game.getPlayerX().getId()) && !userId.equals(Objects.requireNonNull(game.getPlayerO()).getId())) {
            telegramBot.execute(new AnswerCallbackQuery(callbackQuery.id())
                    .text("It's not your game!")
                    .showAlert(true));
            return;
        }

        // 3. Navbat tekshiruvi
        if (game.getInTurn() == Turn.X && !userId.equals(game.getPlayerX().getId()) || game.getInTurn() == Turn.O && !userId.equals(game.getPlayerO().getId())) {

            telegramBot.execute(new AnswerCallbackQuery(callbackQuery.id())
                    .text("Not your turn! Wait for your opponent.")
                    .showAlert(true));

            /*telegramBot.execute(
                    new AnswerCallbackQuery(callbackQuery.id()).text("You played with X").showAlert(true)
            );*/
            return;
        }

        if (board[row][col] != 0) {
            telegramBot.execute(new AnswerCallbackQuery(callbackQuery.id()).text("Invalid move! 👈").showAlert(true));
            return;
        }

        int mark = game.getPlayerX().getId().equals(userId) ? 1 : 2;
        board[row][col] = mark;

        game.setBoard(board);
        game.setUpdatedAt(LocalDateTime.now());

        if (baseLogic.checkWin(board, mark)) {
            game.setStatus(GameStatus.FINISHED);

            String winnerName = mark == 1 ? game.getPlayerX().getFullname() : game.getPlayerO().getFullname();
            String loserName = mark == 1 ? game.getPlayerO().getFullname() : game.getPlayerX().getFullname();

            EditMessageText winMessage = new EditMessageText(
                    game.getInlineMessageId(),
                    "🏆" + winnerName
                            + "\n\uD83D\uDE2D\uFE0F\uFE0F\uFE0F\uFE0F\uFE0F️" + loserName
                            //+ "\n🏆Winner, winner, chicken dinner! "
                            + "\n\n<b>Board:</b>" + formatBoard(game.getBoard())
            )
                    .parseMode(ParseMode.HTML)
                    .replyMarkup(buttonService.endMultiGameBtns());

            telegramBot.execute(winMessage);

            boardCleaner.cleanAndUpdate(game);
            /// history result

        } else if (baseLogic.checkDraw(board)) {
            game.setStatus(GameStatus.FINISHED);

            EditMessageText drawMessage = new EditMessageText(
                    game.getInlineMessageId(),
                    "It's a draw!\n" + game.getPlayerX().getFullname() + " 🤝 " + game.getPlayerO().getFullname()
                            + "\n\n" + formatBoard(game.getBoard())
            )
                    .parseMode(ParseMode.HTML)
                    .replyMarkup(buttonService.endMultiGameBtns());

            telegramBot.execute(drawMessage);
            boardCleaner.cleanAndUpdate(game);
            /// history result

        } else {
            switchTurn(game);
            InlineKeyboardMarkup markup = buttonService.getMultiBoardBtns(gameId, board);

            String text;
            if (game.getInTurn() == Turn.X) {
                text = "❌" + game.getPlayerX().getFullname() + " 👈\n" +
                        "⭕" + game.getPlayerO().getFullname();
            } else {
                text = "❌" + game.getPlayerX().getFullname() + "\n" +
                        "⭕" + game.getPlayerO().getFullname() + " 👈";
            }
            telegramBot.execute(
                    new EditMessageText(game.getInlineMessageId(), text)
                            .replyMarkup(markup)
            );
        }
        gameRepository.save(game);
    }

    public void switchTurn(MultiGame game) {
        if (game.getPlayerO() != null) {
            // Agar ikkala o'yinchi ham mavjud bo'lsa
            game.setInTurn(game.getInTurn() == Turn.X
                    ? Turn.O
                    : Turn.X);
        } else {
            // Agar faqat bitta o'yinchi bo'lsa (masalan, hali ikkinchi o'yinchi qo'shilmagan)
            game.setInTurn(Turn.X); // yoki PLAYER_O - qaysi biri boshlashiga qarab
        }
    }

    private String formatBoard(int[][] board) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n<pre>");
        for (int[] row : board) {
            for (int cell : row) {
                String symbol = switch (cell) {
                    case 1 -> "❌";
                    case 2 -> "⭕";
                    default -> "⬜";
                };
                sb.append(symbol);
            }
            sb.append("\n");
        }
        sb.append("</pre>");
        return sb.toString();
    }
}
