package com.chess.onlinechess.controller;

import com.chess.onlinechess.model.User;
import com.chess.onlinechess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        try {
            User user = userService.registerUser(username, password);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Login is handled by Spring Security Basic Auth for now, or we can add a
    // custom login endpoint if using JWT.
    // For this prototype, we'll verify credentials via a simple protected endpoint.
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(java.security.Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }
}
