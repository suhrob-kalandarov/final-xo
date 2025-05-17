package org.exp.application.repositories.botgame;

import org.exp.application.models.entity.result.BotGameResult;
import org.exp.application.models.enums.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BotGameResultRepository extends JpaRepository<BotGameResult, Long> {

    Optional<BotGameResult> findByPlayerIdAndDifficultyLevel(Long playerId, Difficulty difficultyLevel);

    List<BotGameResult> findAllByPlayerId(Long playerId);
}