package org.exp.application.repositories.common;

import org.exp.application.models.entity.message.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    Language findByCode(String code);
}
