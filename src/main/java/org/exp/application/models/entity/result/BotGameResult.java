package org.exp.application.models.entity.result;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.exp.application.models.basekeys.BaseEntity;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.enums.Difficulty;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bot_game_results")
public class BotGameResult extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "player_id")
    private TgUser player;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level")
    private Difficulty difficultyLevel;

    @Column(name = "win_count")
    private int winCount;

    @Column(name = "draw_count")
    private int drawCount;

    @Column(name = "lose_count")
    private int loseCount;
}
