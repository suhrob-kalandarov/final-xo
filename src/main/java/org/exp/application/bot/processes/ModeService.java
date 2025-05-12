package org.exp.application.bot.processes;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.TgUser;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramSenderService;
import org.exp.application.services.main.TgUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModeService {

    private final TgUserService tgUserService;
    private final TelegramSenderService senderService;
    private final TelegramButtonService buttonService;

    public void sendMainMenu(TgUser tgUser) {
        senderService.sendMessage(tgUser.getId(), "MODE MENU", buttonService.modeMenuBtns());
    }

    public void sendPlayBotMenu(TgUser tgUser) {
        senderService.sendMessage(tgUser.getId(), "", buttonService.chooseXO());
    }
}
