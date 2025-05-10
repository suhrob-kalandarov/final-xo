package org.exp.application.config.ext;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.DeleteWebhook;
import com.pengrad.telegrambot.response.BaseResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteWebhookRunner {

    private final TelegramBot bot;

    @PostConstruct
    public void deleteWebhookIfExists() {
        BaseResponse response = bot.execute(new DeleteWebhook());
        if (response.isOk()) {
            System.out.println("✅ Webhook deleted successfully.");
        } else {
            System.out.println("❌ Failed to delete webhook: " + response.description());
        }
    }
}

