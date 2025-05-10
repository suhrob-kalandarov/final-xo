package org.exp.application.models.basekeys;

import jakarta.persistence.*;
import lombok.*;
import org.exp.application.models.extra.GameStatus;
import org.exp.application.services.board.BoardConverter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseGame extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private GameStatus status = GameStatus.CREATED;

    @Convert(converter = BoardConverter.class)
    @Column(name = "game_board", length = 50)
    private int[][] board = new int[3][3];

    @Column(name = "move_count")
    private Byte moveCount;

    @Column( name = "winner_id")
    private Long winnerId;

    @Column(name = "game_ended_date_time")
    private LocalDateTime endedAt;

    public void initGameBoard() {
        this.board = new int[3][3];
    }
}
