package org.exp.application.services.msg;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.message.Translation;
import org.exp.application.repositories.TranslationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private final TranslationRepository translationRepo;

    // Til kodi va kalit orqali tarjimani olish
    public String getMessage(String key, String langCode) {
        Optional<Translation> translation = translationRepo.findByKeyAndLanguageCode(key, langCode);
        return translation.map(Translation::getValue)
                .orElseGet(() -> translationRepo.findByKeyAndLanguageCode(key, "en") // fallback
                        .map(Translation::getValue)
                        .orElse("N/A"));
    }
}
