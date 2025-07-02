package org.exp.application.config;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.message.Language;
import org.exp.application.models.entity.message.Translation;
import org.exp.application.repositories.common.LanguageRepository;
import org.exp.application.repositories.common.TgUserRepository;
import org.exp.application.repositories.common.TranslationRepository;
import org.exp.application.services.user.TgUserService;
import org.exp.application.utils.LangLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final LanguageRepository languageRepository;
    private final TranslationRepository translationRepository;
    private final LangLoader langLoader;

    public static Language lang4 = new Language("\uD83C\uDDEA\uD83C\uDDF9", "Ethiopia", "Amharic", "am");
    public static Language lang3 = new Language("\uD83C\uDDFA\uD83C\uDDFF", "Uzbekistan", "Uzbek", "uz");
    public static Language lang2 = new Language("\uD83C\uDDF7\uD83C\uDDFA", "Russia", "Русский", "ru");
    public static Language lang1 = new Language("\uD83C\uDDFA\uD83C\uDDF8", "America", "English", "en");
    private final TgUserRepository tgUserRepository;
    private final TgUserService tgUserService;

    @Override
    public void run(String... args) throws Exception {

        if (tgUserRepository.count() == 200) {
            tgUserService.messageSender();
        }

        long langCount = languageRepository.count();
        if (langCount == 0) {
            languageRepository.saveAll(List.of(lang1, lang2, lang3, lang4));
        }

        if (langCount == 2) {
            languageRepository.saveAll(List.of(lang3, lang4));
            List<Translation> translations = langLoader.saveUA2LangMsgs();
            translationRepository.saveAll(translations);
        }

        if (translationRepository.count() == 0){
            List<Translation> translations = langLoader.save2LangMsgs();
            List<Translation> translations2 = langLoader.saveUA2LangMsgs();
            translationRepository.saveAll(translations);
        }
    }
}
