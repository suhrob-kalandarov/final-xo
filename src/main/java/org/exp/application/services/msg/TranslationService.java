package org.exp.application.services.msg;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.message.Language;
import org.exp.application.models.entity.message.Translation;
import org.exp.application.models.entity.session.SessionMenu;
import org.exp.application.repositories.common.TranslationRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private final TranslationRepository translationRepo;

    public Map<String, String> getTranslationsMap(SessionMenu menu, Language language) {
        return translationRepo.findAllByMenuAndLanguage(menu, language)
                .stream()
                .collect(Collectors.toMap(Translation::getKey,Translation::getValue, (val1, val2) -> val1));
    }

    public Translation getTranslation(SessionMenu menu, Language language) {
        return translationRepo.findByMenuAndLanguage(menu, language);
    }

    public String getMessage(String key, Language language) {
        Optional<Translation> translation = translationRepo.findByKeyAndLanguage(key, language);
        return translation.map(Translation::getValue)
                .orElseGet(() -> translationRepo.findByKeyAndLanguageCode(key, "en") // fallback
                        .map(Translation::getValue)
                        .orElse("N/A"));
    }
}
