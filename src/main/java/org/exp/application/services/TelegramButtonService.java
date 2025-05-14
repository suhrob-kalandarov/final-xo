package org.exp.application.services;

import com.pengrad.telegrambot.model.request.*;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.game.BotGame;
import org.exp.application.models.entity.message.Language;
import org.exp.application.repositories.LanguageRepository;
import org.exp.application.services.main.TgUserService;
import org.exp.application.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramButtonService {

    private final LanguageRepository languageRepo;
    private final TgUserService tgUserService;

    public Keyboard homeMenuBtns() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Game Mode").callbackData("game-mode")
        ).addRow(
                new InlineKeyboardButton("Manual").callbackData("game-manual"),
                new InlineKeyboardButton("Language").callbackData("language")
        );
    }

    public Keyboard modeMenuBtns() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Play with bot").callbackData("play-with_bot")
        ).addRow(
                new InlineKeyboardButton("Play with friend").switchInlineQuery(" ")
        ).addRow(
                new InlineKeyboardButton("<< Back to Home").callbackData("back-to_home")
        );
    }

    public Keyboard menuChooseXO(BotGame botGame) {
        Long gameId = botGame.getId();
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton(Constants.X_SIGN).callbackData("sign-x_" + gameId),
                new InlineKeyboardButton(Constants.O_SIGN).callbackData("sign-o_" + gameId)
        );
    }

    public Keyboard botGameMenuBtns(Long gameId) {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Play").callbackData("play_" + gameId)
        ).addRow(
                new InlineKeyboardButton("Difficulty").callbackData("difficulty_" + gameId)
        ).addRow(
                new InlineKeyboardButton("Mode menu").callbackData("back-to_mode-menu")
        ).addRow(
                new InlineKeyboardButton("<< Back to Home").callbackData("back-to_home")
        );
    }

    public Keyboard langMenuBtns() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<Language> languages = languageRepo.findAll();

        int size = languages.size();
        int i = 0;

        // Agar soni toq bo‘lsa, birinchi tilni alohida chiqaramiz
        if (size % 2 != 0) {
            Language firstLang = languages.getFirst();
            inlineKeyboardMarkup.addRow(
                    new InlineKeyboardButton(firstLang.getFlag() + firstLang.getLanguage())
                            .callbackData("LANG_" + firstLang.getId())
            );
            i = 1; // Ikkinchisidan boshlab 2tadan chiqaramiz
        }

        // Qolgan tillarni 2 tadan chiqarish
        for (; i < size; i += 2) {
            Language lang1 = languages.get(i);
            Language lang2 = languages.get(i + 1);

            inlineKeyboardMarkup.addRow(
                    new InlineKeyboardButton(lang1.getFlag() + lang1.getLanguage())
                            .callbackData("LANG_" + lang1.getId()),
                    new InlineKeyboardButton(lang2.getFlag() + lang2.getLanguage())
                            .callbackData("LANG_" + lang2.getId())
            );
        }

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getBoardBtns(long gameId, int[][] board) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        for (int i = 0; i < board.length; i++) {
            InlineKeyboardButton[] row = new InlineKeyboardButton[board[i].length];
            for (int j = 0; j < board[i].length; j++) {
                String cellText = switch (board[i][j]) {
                    case 1 -> "❌";
                    case 2 -> "⭕";
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
                new InlineKeyboardButton("🔄").switchInlineQueryCurrentChat(" "),
                new InlineKeyboardButton("🤖").url("https://t.me/" + "xoDemoBot")
        );
    }
}
