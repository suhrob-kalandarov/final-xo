package org.exp.application.services;

import lombok.RequiredArgsConstructor;
import org.exp.application.bot.processes.botgame.BotGameService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameCenter {

    private final BotGameService botGameService;
    private final TelegramSenderService senderService;
    private final TelegramEditService editService;
    private final TelegramButtonService buttonService;


}
