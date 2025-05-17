package org.exp.application.bot.processes.botgame;

import lombok.RequiredArgsConstructor;
import org.exp.application.bot.processes.CabinetService;
import org.exp.application.models.entity.TgUser;
import org.exp.application.services.user.TgUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackButtonService {

    private final TgUserService tgUserService;
    private final CabinetService cabinetService;

    public void execute(String data, Long userId) {

        if (data.equals("back-to_home")) {

            TgUser tgUser = tgUserService.getById(userId);
            cabinetService.editSendHome(tgUser.getId());

        } else if (data.equals("back-to_bot-game-menu")) {
            //modeService.sendModeMenu(userId);
        }
    }
}
