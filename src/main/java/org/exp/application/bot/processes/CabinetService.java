package org.exp.application.bot.processes;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.TgUser;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramSenderService;
import org.exp.application.services.main.TgUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CabinetService {

    private final TgUserService tgUserService;
    private final TelegramSenderService senderService;
    private final TelegramButtonService buttonService;

    public void menu(TgUser tgUser) {
        senderService.sendMessage(
                tgUser.getId(),
                "MAIN_MENU (HOME)",
                buttonService.homeMenuBtns()
        );
    }


}
