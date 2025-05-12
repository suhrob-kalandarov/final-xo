package org.exp.application.config;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.extra.BotLanguage;
import org.exp.application.repositories.BotLanguageRepository;
import org.exp.application.repositories.TgUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final TgUserRepository tgUserRepository;
    private final BotLanguageRepository languageRepository;
    static BotLanguage lang3;

    @Override
    public void run(String... args) throws Exception {

        if (languageRepository.count() == 0) {
            BotLanguage lang1 = new BotLanguage("Uzbekistan", "\uD83C\uDDFA\uD83C\uDDFF", "Uzbek", "uz");
            BotLanguage lang2 = new BotLanguage("Russia", "\uD83C\uDDF7\uD83C\uDDFA", "Русский", "ru");
            lang3 = new BotLanguage("America", "\uD83C\uDDFA\uD83C\uDDF8", "English", "en");

            languageRepository.saveAll(List.of(lang1, lang2, lang3));
        }
    }

    public BotLanguage getDefLang(){
        return lang3;
    }
}
