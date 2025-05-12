package org.exp.application.services;

import com.pengrad.telegrambot.model.request.*;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.extra.BotLanguage;
import org.exp.application.repositories.BotLanguageRepository;
import org.exp.application.services.main.TgUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramButtonService {

    private final BotLanguageRepository botLanguageRepo;
    private final TgUserService tgUserService;

    public Keyboard cabinetMenuBtns() { // Long userId
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Game Mode").callbackData("game-mode")
        ).addRow(
                new InlineKeyboardButton("Info").callbackData("game-info"),
                new InlineKeyboardButton("Settings").callbackData("settings")
        );
    }

    public Keyboard modeMenuBtns() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Play with bot").callbackData("play-with_bot")
        ).addRow(
                new InlineKeyboardButton("Play with friend").switchInlineQuery(" ")
        ).addRow(
                new InlineKeyboardButton("Back to Home").callbackData("back-to_home")
        );
    }

    public Keyboard chooseXO() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("X").callbackData("play-with_x"),
                new InlineKeyboardButton("O").callbackData("play-with_o")
        ).addRow(
                new InlineKeyboardButton("Back").callbackData("back-to_mode-cabinet")
        );
    }

    public Keyboard langMenuBtns() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<BotLanguage> languages = botLanguageRepo.findAll();

        int size = languages.size();
        int i = 0;

        // Agar soni toq bo‘lsa, birinchi tilni alohida chiqaramiz
        if (size % 2 != 0) {
            BotLanguage firstLang = languages.getFirst();
            inlineKeyboardMarkup.addRow(
                    new InlineKeyboardButton(firstLang.getFlag() + firstLang.getName())
                            .callbackData("LANG_" + firstLang.getId())
            );
            i = 1; // Ikkinchisidan boshlab 2tadan chiqaramiz
        }

        // Qolgan tillarni 2 tadan chiqarish
        for (; i < size; i += 2) {
            BotLanguage lang1 = languages.get(i);
            BotLanguage lang2 = languages.get(i + 1);

            inlineKeyboardMarkup.addRow(
                    new InlineKeyboardButton(lang1.getFlag() + lang1.getName())
                            .callbackData("LANG_" + lang1.getId()),
                    new InlineKeyboardButton(lang2.getFlag() + lang2.getName())
                            .callbackData("LANG_" + lang2.getId())
            );
        }

        return inlineKeyboardMarkup;
    }
}
