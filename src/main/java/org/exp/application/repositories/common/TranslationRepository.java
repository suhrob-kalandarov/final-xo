package org.exp.application.repositories.common;

import org.exp.application.models.entity.message.Language;
import org.exp.application.models.entity.message.Translation;
import org.exp.application.models.entity.session.SessionMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    Optional<Translation> findByKeyAndLanguageCode(String key, String langCode);

    List<Translation> findAllByMenuAndLanguage(SessionMenu menu, Language language);

    Optional<Translation> findByKeyAndLanguage(String key, Language language);

    Translation findByMenuAndLanguage(SessionMenu menu, Language language);
}
