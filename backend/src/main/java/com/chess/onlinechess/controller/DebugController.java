package com.chess.onlinechess.controller;

import com.chess.onlinechess.model.User;
import com.chess.onlinechess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/test-password")
    public Map<String, Object> testPassword(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String rawPassword = payload.get("password");

        User user = userRepository.findByUsername(username).orElse(null);

        Map<String, Object> result = new HashMap<>();
        result.put("userExists", user != null);

        if (user != null) {
            result.put("storedPasswordHash", user.getPassword());
            result.put("providedPassword", rawPassword);
            result.put("passwordMatches", passwordEncoder.matches(rawPassword, user.getPassword()));
        }

        return result;
    }
}
