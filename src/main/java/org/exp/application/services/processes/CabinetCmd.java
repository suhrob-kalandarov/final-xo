package org.exp.application.services.processes;

import com.pengrad.telegrambot.model.Message;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Service;

@Service
public class CabinetCmd implements DataHandler<Message> {

    @Override
    public void handle(Message message) {
        Long userId = message.from().id();



    }
}
