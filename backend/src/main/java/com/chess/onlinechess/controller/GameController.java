package com.chess.onlinechess.controller;

import com.chess.onlinechess.model.Game;
import com.chess.onlinechess.model.User;
import com.chess.onlinechess.service.GameService;
import com.chess.onlinechess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Game createGame(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        return gameService.createGame(user);
    }

    // Join endpoint removed/deprecated

    @PostMapping("/{id}/move")
    public ResponseEntity<Game> makeMove(@PathVariable Long id, @RequestBody Map<String, String> payload,
            Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        String fen = payload.get("fen");
        return ResponseEntity.ok(gameService.makeMove(id, user, fen));
    }

    @GetMapping
    public List<Game> getRecentGames() {
        // Return active games instead of "open" lobbies
        return gameService.getOpenGames();
    }

    @GetMapping("/{id}")
    public Game getGame(@PathVariable Long id) {
        return gameService.getGame(id).orElseThrow();
    }
}
