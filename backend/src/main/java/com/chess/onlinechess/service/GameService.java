package com.chess.onlinechess.service;

import com.chess.onlinechess.model.Game;
import com.chess.onlinechess.model.GameStatus;
import com.chess.onlinechess.model.User;
import com.chess.onlinechess.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Game createGame(User player) {
        Game game = new Game();
        // unique for single device: player controls both sides validation-wise,
        // but we treat the creator as both or just white for history.
        game.setWhitePlayer(player);
        game.setBlackPlayer(player); // Self-play / Hotseat
        game.setStatus(GameStatus.IN_PROGRESS); // Start immediately
        game.setFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        game.setStartTime(LocalDateTime.now());
        return gameRepository.save(game);
    }

    // Join logic removed for single device as per requirement

    public Game makeMove(Long gameId, User player, String newFen) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            throw new RuntimeException("Game is not in progress");
        }

        // Single device: trust the move if it's valid chess (handled by
        // frontend/library mostly)
        // We do NOT check if 'player' matches the current turn color,
        // because the same human might move both.

        game.setFen(newFen);
        // Update PGN/History if needed

        return gameRepository.save(game);
    }

    public List<Game> getOpenGames() {
        return gameRepository.findByStatus(GameStatus.IN_PROGRESS);
    }

    public Optional<Game> getGame(Long id) {
        return gameRepository.findById(id);
    }
}
