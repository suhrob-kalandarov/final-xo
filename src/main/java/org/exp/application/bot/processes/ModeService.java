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
        senderService.sendMessage(tgUser.getId(), "MODE_MENU", buttonService.modeMenuBtns());
    }

    public void sendSignMenu(TgUser tgUser) {
        senderService.sendMessage(tgUser.getId(), "CHOOSE_SIGN_MENU", buttonService.menuChooseXO());
    }
}
