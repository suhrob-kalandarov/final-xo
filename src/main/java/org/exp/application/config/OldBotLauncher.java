package org.exp.application.config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OldBotLauncher implements CommandLineRunner {

    private static final TelegramBot bot = new TelegramBot("7958534479:AAGSkglSMoQgb8tqeco97KKwh0j2sTv3EjU");

    private static final String message = """
            <blockquote>
            🇺🇸
            🌟 <b>Bot Updated!</b> 🎉 <i>This bot is no longer active. Switch to our <a href="https://t.me/xoBrainBot">new bot</a> for a better experience!</i> 🚀 #BotUpdate
            </blockquote>
            
            <blockquote>
            🇷🇺
            🌟 <b>Бот обновился!</b> 🎉 <i>Этот бот больше не работает. Перейдите на наш <a href="https://t.me/xoBrainBot">новый бот</a> для лучшего опыта!</i> 🚀 #ОбновлениеБота
            </blockquote>
            
            <blockquote>
            🇺🇿
            🌟 <b>Bot yangilandi!</b> 🎉 <i>Bu bot endi ishlamaydi. <a href="https://t.me/xoBrainBot">Yangi botimizga</a> o‘ting va tajribani yaxshilang!</i> 🚀 #BotYangilanishi
            </blockquote>
            
            <blockquote>
            🇪🇹
            🌟 <b>ቦታችን ተሻሽሏል!</b> 🎉 <i>ይህ ቦት ከእንግዲህ አይሰራም። ለተሻለ ተሞክሮ <a href="https://t.me/xoBrainBot">ወደ አዲሱ ቦታችን</a> ይሸጋገሩ!</i> 🚀 #BotUpdate
            </blockquote>
            """;

    @Override
    public void run(String... args) {
        log.info("Eski bot ishga tushdi, yangilanishlarni tinglash boshlandi.");
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                Long userId = null;
                if (update.message() != null) {
                    userId = update.message().from().id();
                } else if (update.callbackQuery() != null) {
                    userId = update.callbackQuery().from().id();
                } else if (update.inlineQuery() != null) {
                    userId = update.inlineQuery().from().id();
                } else {
                    log.info("Noma‘lum yangilanish: {}", update);
                    continue;
                }

                if (userId != null) {
                    msgAlert(userId);
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> log.error("Yangilanishlarni tinglashda xatolik: {}", e.getMessage(), e));
    }

    private void msgAlert(Long id) {
        try {
            SendMessage sendMessage = new SendMessage(id, message)
                    .parseMode(ParseMode.HTML);
            SendResponse response = bot.execute(sendMessage);

            if (response.isOk()) {
                log.info("Xabar muvaffaqiyatli yuborildi, userId: {}", id);
            } else {
                log.error("Xabar yuborishda xatolik, userId: {}, xato: {}", id, response.description());
            }

            // Telegram API cheklovi uchun 100 ms kechikish
            Thread.sleep(100);
        } catch (Exception e) {
            log.error("Xabar yuborishda xatolik, userId: {}, xato: {}", id, e.getMessage(), e);
        }
    }
}