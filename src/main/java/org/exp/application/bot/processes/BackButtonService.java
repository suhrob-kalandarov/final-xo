package org.exp.application.bot.processes;

import lombok.RequiredArgsConstructor;
import org.exp.application.services.TelegramButtonService;
import org.exp.application.services.TelegramSenderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackButtonService {

    private final TelegramSenderService senderService;
    private final TelegramButtonService buttonService;

    private final CabinetService cabinetService;
    private final ModeService modeService;

    public void execute(String data, Long userId) {

        if (data.equals("back-to_home")) {
            cabinetService.menu(userId);

        } else if (data.equals("back-to_mode-menu")) {
            modeService.sendMainMenu(userId);

        }
    }
}
