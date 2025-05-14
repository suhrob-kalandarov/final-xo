package org.exp.application.config;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.message.Language;
import org.exp.application.repositories.LanguageRepository;
import org.exp.application.repositories.TgUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final TgUserRepository tgUserRepository;
    private final LanguageRepository languageRepository;
    public static Language lang;

    @Override
    public void run(String... args) throws Exception {

        if (languageRepository.count() == 0) {
            Language lang3 = new Language("\uD83C\uDDFA\uD83C\uDDFF", "Uzbekistan", "Uzbek", "uz");
            Language lang2 = new Language("\uD83C\uDDF7\uD83C\uDDFA", "Russia", "Русский", "ru");
            lang = new Language("\uD83C\uDDFA\uD83C\uDDF8", "America", "English", "en");

            languageRepository.saveAll(List.of(lang, lang2, lang3));
        }
    }

    public Language getDefLang(){
        return lang;
    }
}
