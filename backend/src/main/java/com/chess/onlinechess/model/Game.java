package com.chess.onlinechess.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "white_player_id")
    private User whitePlayer;

    @ManyToOne
    @JoinColumn(name = "black_player_id")
    private User blackPlayer;

    @Lob
    private String fen; // Current board state

    @Lob
    private String pgn; // Game history

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
