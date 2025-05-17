package org.exp.application.repositories.common;

import org.exp.application.models.entity.TgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TgUserRepository extends JpaRepository<TgUser, Long> {
    boolean existsByUsername(String username);


    /*@Transactional
    @Modifying
    @Query("UPDATE TgUser t SET t.messageId = :messageId WHERE t.id = :userId")
    int updateMessageIdByUserId(@Param("userId") Long userId, @Param("messageId") Integer messageId);

     @Transactional
    @Modifying
    @Query("update TgUser t set t.language = ?1 where t.id =?2")
    void updateLanguage(@Nullable Language language, Long id);
*/

    //boolean existsByUsername(String username);
}
