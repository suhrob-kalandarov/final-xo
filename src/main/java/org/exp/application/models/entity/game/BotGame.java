package org.exp.application.models.entity.game;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.exp.application.models.basekeys.BaseGame;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.enums.Difficulty;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bot_games")
public class BotGame extends BaseGame {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private TgUser player;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(name = "message_id")
    private Integer messageId;

    @Column(name = "player_symbol")
    private String playerSign;

    public BotGame(TgUser player) {
        this.player = player;
    }
}
