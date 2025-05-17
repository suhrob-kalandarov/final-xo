package org.exp.application.config;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.message.Language;
import org.exp.application.models.entity.message.Translation;
import org.exp.application.repositories.common.LanguageRepository;
import org.exp.application.repositories.common.TgUserRepository;
import org.exp.application.repositories.common.TranslationRepository;
import org.exp.application.utils.LangLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final TgUserRepository tgUserRepository;
    private final LanguageRepository languageRepository;
    private final TranslationRepository translationRepository;
    private final LangLoader langLoader;

    public static Language lang1;
    public static Language lang2;

    @Override
    public void run(String... args) throws Exception {

        if (languageRepository.count() == 0) {
            //Language lang3 = new Language("\uD83C\uDDFA\uD83C\uDDFF", "Uzbekistan", "Uzbek", "uz");
            lang2 = new Language("\uD83C\uDDF7\uD83C\uDDFA", "Russia", "Русский", "ru");
            lang1 = new Language("\uD83C\uDDFA\uD83C\uDDF8", "America", "English", "en");

            //languageRepository.saveAll(List.of(lang, lang2, lang3));
            languageRepository.saveAll(List.of(lang1, lang2));
        }

        if (translationRepository.count() == 0){
            List<Translation> translations = langLoader.save2LangMsgs();
            translationRepository.saveAll(translations);
        }
    }
}
