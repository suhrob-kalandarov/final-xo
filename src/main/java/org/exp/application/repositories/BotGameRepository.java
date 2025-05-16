package org.exp.application.repositories;

import jakarta.transaction.Transactional;
import org.exp.application.models.entity.game.BotGame;
import org.exp.application.models.enums.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotGameRepository extends JpaRepository<BotGame, Long> {


    Optional<BotGame> findByPlayerId(Long id);

    boolean existsByPlayer_Id(Long playerId);

    @Modifying
    @Transactional
    @Query("UPDATE BotGame b SET b.difficulty = :difficulty WHERE b.player.id = :userId")
    int updateDifficultyByUserId(@Param("userId") Long userId,
                                 @Param("difficulty") Difficulty difficulty);
}
