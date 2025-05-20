package org.exp.application.repositories.multigame;

import org.exp.application.models.entity.game.MultiGame;
import org.exp.application.models.enums.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MultiGameRepository extends JpaRepository<MultiGame, Long> {

    Optional<MultiGame> findFirstByStatus(GameStatus status);

    List<MultiGame> findAllByStatusIn(List<GameStatus> created);
}
