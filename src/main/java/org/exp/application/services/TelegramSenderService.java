package org.exp.application.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramSenderService {

    private final TelegramBot bot;

    public Integer execute(EditMessageText editMessageText) {
        SendResponse response = (SendResponse) bot.execute(editMessageText);
        return response.message().messageId();
    }

    public Integer execute(SendMessage sendMessage) {
        SendResponse response = bot.execute(sendMessage);
        return response.message().messageId();
    }

    public void sendMessage(Long chatId, String text) {
        bot.execute(
                new SendMessage(chatId, text)
        );
    }

    public Integer sendMessage(Long chatId, String text, Keyboard keyboard) {
        SendResponse response = bot.execute(new SendMessage(chatId, text).replyMarkup(keyboard));
        return response.message().messageId();
    }

    /*public void sendEditMessageWithInlineBtn(TgUser tgUser, String text, ReplyKeyboardMarkup keyboardMarkup) {
        bot.execute(new EditMessageText(tgUser.getId(), text)
                .replyMarkup(keyboardMarkup)
        );
    }*/
}
