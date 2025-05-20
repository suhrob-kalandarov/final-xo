/*
package org.exp.application.models.entity.result;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.exp.application.models.basekeys.BaseResult;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.enums.GameStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "multi_game_results")
public class MultiGameResult extends BaseResult {

    @Column(name = "creator_id")
    private Long creatorId;

    @Column( name = "winner_id")
    private Long winnerId;

    @Column(name = "game_ended_date_time")
    private LocalDateTime endedAt;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_x")
    private TgUser playerX;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_o")
    private TgUser playerO;
}
*/
