package org.exp.application.services;

import com.pengrad.telegrambot.model.request.*;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.game.BotGame;
import org.exp.application.models.entity.message.Language;
import org.exp.application.models.enums.Difficulty;
import org.exp.application.repositories.LanguageRepository;
import org.exp.application.services.main.TgUserService;
import org.exp.application.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.exp.application.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class TelegramButtonService {

    private final LanguageRepository languageRepo;
    private final TgUserService tgUserService;

    public Keyboard homeMenuBtns() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Play with bot").callbackData("bot-main-menu")
        ).addRow(
                new InlineKeyboardButton("Play with friend").switchInlineQuery(" play")
        ).addRow(
                new InlineKeyboardButton("Manual Info").callbackData("game-manual"),
                new InlineKeyboardButton("Language").callbackData("user-language")
        );
    }

    public Keyboard botGameMenuBtns() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Play").callbackData("bot-game-play")
        ).addRow(
                new InlineKeyboardButton("Difficulty").callbackData("bot-game-difficulty")
        ).addRow(
                new InlineKeyboardButton("Back").callbackData("user-back-to_home")
        );
    }

    public Keyboard menuChooseXO(BotGame botGame) {
        Long gameId = botGame.getId();
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton(Constants.X_SIGN).callbackData("bot-game-player-sign-x_" + gameId),
                new InlineKeyboardButton(Constants.O_SIGN).callbackData("bot-game-player-sign-o_" + gameId)
        );
    }

    public InlineKeyboardMarkup difficultyMenuButtons(){
        return new InlineKeyboardMarkup()
                .addRow(
                        new InlineKeyboardButton(LEVEL_EASY).callbackData(LEVEL + Difficulty.EASY)
                )
                .addRow(
                        new InlineKeyboardButton(LEVEL_AVERAGE).callbackData(LEVEL + Difficulty.MEDIUM),
                        new InlineKeyboardButton(LEVEL_DIFFICULT).callbackData(LEVEL + Difficulty.HARD)
                )
                .addRow(
                        new InlineKeyboardButton(LEVEL_EXTREME).callbackData(LEVEL + Difficulty.EXTREME)
                )
                /*.addRow(
                        new InlineKeyboardButton(BACK_BUTTON_MSG).callbackData("user-back-to_bot-game-menu")
                )*/
                ;
    }

    public InlineKeyboardMarkup getBoardBtns(long gameId, int[][] board, String playerSign) {
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

    public InlineKeyboardMarkup endMultiGameBtns() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("🔄").switchInlineQueryCurrentChat(" play"),
                new InlineKeyboardButton("🤖").url("https://t.me/" + "xoDemoBot")
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
}
