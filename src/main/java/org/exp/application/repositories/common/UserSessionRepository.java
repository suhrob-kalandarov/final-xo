package org.exp.application.repositories.common;

import io.micrometer.common.lang.Nullable;
import jakarta.transaction.Transactional;
import org.exp.application.models.entity.message.Language;
import org.exp.application.models.entity.session.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    @Transactional
    @Modifying
    @Query("update UserSession t set t.language = ?1 where t.userId =?2")
    void updateLanguage(@Nullable Language language, Long userId);
}
