package org.exp.application.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramEditService {

    private final TelegramBot bot;
    private final TelegramButtonService buttonService;

    public Integer execute(EditMessageText editMessageText) {
        SendResponse response = (SendResponse) bot.execute(editMessageText);
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

    public Integer editMessage(Long id, Integer messageId, String text, InlineKeyboardMarkup button) {
        SendResponse response = (SendResponse) bot.execute(new EditMessageText(id, messageId, text).replyMarkup(button));
        return response.message().messageId();
    }

    public Integer editMessage(Long id, Integer messageId, String text, InlineKeyboardMarkup button, ParseMode parseMode) {
        SendResponse response = (SendResponse) bot.execute(new EditMessageText(id, messageId, text).parseMode(parseMode).replyMarkup(button));
        return response.message().messageId();
    }
}
