package com.chess.onlinechess.repository;

import com.chess.onlinechess.model.Game;
import com.chess.onlinechess.model.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByStatus(GameStatus status);
}
