package org.exp.application.services.handlers;

import com.pengrad.telegrambot.model.ChosenInlineResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.application.usekeys.DataHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChosenInlineResultHandler implements DataHandler<ChosenInlineResult> {

    @Override
    public void handle(ChosenInlineResult chosenInlineResult) {

    }
}