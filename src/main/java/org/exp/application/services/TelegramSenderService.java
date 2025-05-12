package org.exp.application.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramSenderService {

    private final TelegramBot bot;

    public void sendMessage(Long chatId, String text) {
        bot.execute(
                new SendMessage(chatId, text)
        );
    }

    public void sendMessage(Long chatId, String text, Keyboard keyboard) {
        bot.execute(new SendMessage(chatId, text).replyMarkup(keyboard));
    }

    /*public void sendEditMessageWithInlineBtn(TgUser tgUser, String text, ReplyKeyboardMarkup keyboardMarkup) {
        bot.execute(new EditMessageText(tgUser.getId(), text)
                .replyMarkup(keyboardMarkup)
        );
    }*/
}
