package org.exp.application.repositories;

import org.exp.application.models.entity.TgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TgUserRepository extends JpaRepository<TgUser, Long> {
    boolean existsByUsername(String username);


    @Transactional
    @Modifying
    @Query("UPDATE TgUser t SET t.messageId = :messageId WHERE t.id = :userId")
    int updateMessageIdByUserId(@Param("userId") Long userId, @Param("messageId") Integer messageId);


    //boolean existsByUsername(String username);
}
