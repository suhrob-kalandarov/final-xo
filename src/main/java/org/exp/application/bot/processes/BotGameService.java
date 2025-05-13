package org.exp.application.bot.processes;

import lombok.RequiredArgsConstructor;
import org.exp.application.services.BotGameLogic;
import org.exp.application.services.board.BoardChecker;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotGameService {

    private final BotGameLogic botGameLogic;
    private final BoardChecker boardChecker;


}
