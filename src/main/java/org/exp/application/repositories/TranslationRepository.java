package org.exp.application.repositories;

import org.exp.application.models.entity.message.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    Optional<Translation> findByKeyAndLanguageCode(String key, String langCode);
}
