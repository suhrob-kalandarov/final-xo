package org.exp.application.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.TgUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramSenderService {

    private TelegramBot bot;

    public void sendMessage(Long chatId, String text) {
        bot.execute(
                new SendMessage(chatId, text)
        );
    }

    public void sendMessage(Long chatId, String text, ReplyKeyboardMarkup keyboardMarkup) {
        bot.execute(new SendMessage(chatId, text)
                .replyMarkup(keyboardMarkup)
        );
    }

    /*public void sendEditMessageWithInlineBtn(TgUser tgUser, String text, ReplyKeyboardMarkup keyboardMarkup) {
        bot.execute(new EditMessageText(tgUser.getId(), text)
                .replyMarkup(keyboardMarkup)
        );
    }*/
}
