package org.exp.application.bot.handlers;

import com.pengrad.telegrambot.model.InlineQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InlineHandler implements DataHandler<InlineQuery> {

    @Override
    public void handle(InlineQuery inlineQuery) {

    }
}