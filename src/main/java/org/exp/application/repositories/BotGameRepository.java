package org.exp.application.repositories;

import org.exp.application.models.entity.game.BotGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotGameRepository extends JpaRepository<BotGame, Long> {


    Optional<BotGame> findByPlayerId(Long id);
}
