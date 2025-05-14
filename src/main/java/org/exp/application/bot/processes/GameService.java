package org.exp.application.bot.processes;

import lombok.RequiredArgsConstructor;
import org.exp.application.services.main.TgUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final TgUserService tgUserService;

    public void createAndStartGame() {


    }
}
