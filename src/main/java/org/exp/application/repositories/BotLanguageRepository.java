package org.exp.application.repositories;

import org.exp.application.models.entity.extra.BotLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotLanguageRepository extends JpaRepository<BotLanguage, Long> {

}
