package org.exp.application.bot.processes;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.game.BotGame;
import org.exp.application.models.entity.TgUser;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramEditService;
import org.exp.application.services.TelegramSenderService;
import org.exp.application.services.main.TgUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModeService {

    private final TgUserService tgUserService;
    private final BotGameService botGameService;
    private final TelegramSenderService senderService;
    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;

    @Transactional
    public void sendMainMenu(Long userId) {
        TgUser tgUser = tgUserService.getById(userId);

        //Integer messageId = senderService.sendMessage(tgUser.getId(), "MODE_MENU", buttonService.modeMenuBtns());

        Integer messageId = editService.editMessage(
                tgUser.getId(), tgUser.getMessageId(),
                "MODE_MENU",
                (InlineKeyboardMarkup) buttonService.modeMenuBtns()
        );

        tgUser.setMessageId(messageId);
        tgUserService.save(tgUser);
    }

    public void sendSignMenu(Long userId) {
        TgUser tgUser = tgUserService.getById(userId);
        BotGame botGame = botGameService.getOrCreateBotGame(tgUser);

        //Integer messageId = senderService.sendMessage(tgUser.getId(), "CHOOSE_SIGN_MENU", buttonService.menuChooseXO(botGame));

        Integer editMessageId = editService.editMessage(
                tgUser.getId(), tgUser.getMessageId(),
                "CHOOSE_SIGN_MENU",
                (InlineKeyboardMarkup) buttonService.menuChooseXO(botGame)
        );

        botGame.setMessageId(editMessageId);
        botGameService.save(botGame);
    }

    public void sendSignMenu(String data) {
        Long gameId = Long.parseLong(data.split("_")[1]);
        Optional<BotGame> optionalBotGame = botGameService.findById(gameId);

        if (optionalBotGame.isEmpty()) {
            System.out.println("ModeService.sendSignMenu");
            return;
        }

        BotGame botGame = optionalBotGame.get();

        sendSignMenu(botGame);
    }

    public void sendSignMenu(BotGame botGame) {
        Integer editMessageId = editService.editMessage(
                botGame.getPlayer().getId(), botGame.getMessageId(),
                "CHOOSE_SIGN_MENU",
                (InlineKeyboardMarkup) buttonService.menuChooseXO(botGame)
        );

        botGame.setMessageId(editMessageId);
        botGameService.save(botGame);
    }
}
