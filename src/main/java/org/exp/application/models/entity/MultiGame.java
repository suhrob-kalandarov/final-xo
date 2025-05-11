package org.exp.application.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.application.models.basekeys.BaseGame;
import org.exp.application.models.enums.Turn;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "multi_games")
public class MultiGame extends BaseGame {

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "in_turn")
    @Enumerated(EnumType.STRING)
    private Turn inTurn;

    @Column(name = "inline_message_id")
    private String inlineMessageId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_x")
    private TgUser playerX;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_o")
    private TgUser playerO;
}
