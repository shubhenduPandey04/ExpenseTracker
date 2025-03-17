package com.expensetracker.controller;

import com.expensetracker.model.User;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Ensure it's injected

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ User Registration (Encode password before saving)
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "User already exists";
        }
        // Ensure password is encoded
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    // ✅ User Login (Validate with encoded password)
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User storedUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate password
        if (!passwordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}
