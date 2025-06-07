package org.exp.application.services;

import com.pengrad.telegrambot.model.request.*;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.message.Language;
import org.exp.application.models.enums.Difficulty;
import org.exp.application.repositories.common.LanguageRepository;
import org.exp.application.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.exp.application.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class TelegramButtonService {
    private final LanguageRepository languageRepo;

    public Keyboard homeMenuBtns(Map<String, String> messages) {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton(messages.get(PLAY_WITH_BOT_BTN)).callbackData("bot-main-menu")
        ).addRow(
                new InlineKeyboardButton(messages.get(PLAY_WITH_FRIEND_BTN)).switchInlineQuery(" play")
        ).addRow(
                new InlineKeyboardButton(messages.get(SUPPORT_BTN)).callbackData("user-getOrCreate-bot-info"),
                new InlineKeyboardButton(messages.get(LANGUAGE_BTN)).callbackData("user-language")
        );
    }

    public Keyboard botGameMenuBtns(Map<String, String> messages) {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton(messages.get(PLAY)).callbackData("bot-game-play")
        ).addRow(
                new InlineKeyboardButton(messages.get(DIFFICULTY_LEVEL_BTN)).callbackData("bot-game-difficulty")
        ).addRow(
                new InlineKeyboardButton(messages.get(BACK_BTN)).callbackData("user-back-to_home")
        );
    }

    public Keyboard menuChooseXO(Long gameId) {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton(Constants.X_SIGN).callbackData("bot-game-player-sign-x_" + gameId),
                new InlineKeyboardButton(Constants.O_SIGN).callbackData("bot-game-player-sign-o_" + gameId)
        );
    }

    public InlineKeyboardMarkup difficultyMenuButtons(Map<String, String> messages){
        return new InlineKeyboardMarkup()
                .addRow(
                        new InlineKeyboardButton(messages.get(LEVEL_EASY)).callbackData(LEVEL + Difficulty.EASY)
                )
                .addRow(
                        new InlineKeyboardButton(messages.get(LEVEL_MEDIUM)).callbackData(LEVEL + Difficulty.MEDIUM),
                        new InlineKeyboardButton(messages.get(LEVEL_HARD)).callbackData(LEVEL + Difficulty.HARD)
                )
                .addRow(
                        new InlineKeyboardButton(messages.get(LEVEL_EXTREME)).callbackData(LEVEL + Difficulty.EXTREME)
                )
                /*.addRow(
                        new InlineKeyboardButton(BACK_BUTTON_MSG).callbackData("user-back-to_bot-game-menu")
                )*/
                ;
    }

    public InlineKeyboardMarkup getBotBoardBtns(long gameId, int[][] board, String playerSign) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        String playerEmoji = playerSign.equals(Constants.X_SIGN) ? "❌" : "⭕";
        String botEmoji = playerSign.equals(Constants.X_SIGN) ? "⭕" : "❌";

        for (int i = 0; i < board.length; i++) {
            InlineKeyboardButton[] row = new InlineKeyboardButton[board[i].length];
            for (int j = 0; j < board[i].length; j++) {
                String cellText = switch (board[i][j]) {
                    case 1 -> playerEmoji;
                    case 2 -> botEmoji;
                    default -> "⬜";
                };
                row[j] = new InlineKeyboardButton(cellText)
                        .callbackData("bot-game-cell_" + gameId + "_" + i + "_" + j);
            }
            markup.addRow(row);
        }
        return markup;
    }

    public InlineKeyboardMarkup getMultiBoardBtns(Long gameId, int[][] board) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        for (int i = 0; i < board.length; i++) {
            InlineKeyboardButton[] row = new InlineKeyboardButton[board[i].length];
            for (int j = 0; j < board[i].length; j++) {
                String cellText = switch (board[i][j]) {
                    case 1 -> X_SIGN;
                    case 2 -> O_SIGN;
                    default -> "⬜";
                };
                row[j] = new InlineKeyboardButton(cellText)
                        .callbackData("MOVE_" + gameId + "_" + i + "_" + j);
            }
            markup.addRow(row);
        }
        return markup;
    }

    public InlineKeyboardMarkup endMultiGameBtns() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("🔄").switchInlineQueryCurrentChat(" play"),
                new InlineKeyboardButton("🤖").url("https://t.me/" + "xoBrainBot")
        );
    }

    public Keyboard langMenuBtns() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<Language> languages = languageRepo.findAll();

        int size = languages.size();
        int i = 0;

        if (size % 2 != 0) {
            Language firstLang = languages.getFirst();
            inlineKeyboardMarkup.addRow(
                    new InlineKeyboardButton(firstLang.getFlag() + firstLang.getLanguage())
                            .callbackData("user-lang_" + firstLang.getId())
            );
            i = 1;
        }

        for (; i < size; i += 2) {
            Language lang1 = languages.get(i);
            Language lang2 = languages.get(i + 1);

            inlineKeyboardMarkup.addRow(
                    new InlineKeyboardButton(lang1.getFlag() + lang1.getLanguage())
                            .callbackData("user-lang_" + lang1.getId()),
                    new InlineKeyboardButton(lang2.getFlag() + lang2.getLanguage())
                            .callbackData("user-lang_" + lang2.getId())
            );
        }

        return inlineKeyboardMarkup;
    }


    public InlineKeyboardMarkup backBtn(String msg){
        return  new InlineKeyboardMarkup(
                new InlineKeyboardButton(msg)
                        .callbackData("user-back-to_home")
        );
    }
}
