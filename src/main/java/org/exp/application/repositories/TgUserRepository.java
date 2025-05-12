package org.exp.application.repositories;

import org.exp.application.models.entity.TgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TgUserRepository extends JpaRepository<TgUser, Long> {
    boolean existsByUsername(String username);
    //boolean existsByUsername(String username);
}
