package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.model.CallbackQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallbackHandler implements DataHandler<CallbackQuery> {

    @Override
    public void handle(CallbackQuery callbackQuery) {

    }
}