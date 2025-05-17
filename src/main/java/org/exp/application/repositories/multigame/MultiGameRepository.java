package org.exp.application.repositories.multigame;

import org.exp.application.models.entity.game.MultiGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultiGameRepository extends JpaRepository<MultiGame, Long> {

}
