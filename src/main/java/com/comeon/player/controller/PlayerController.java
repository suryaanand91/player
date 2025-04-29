package com.comeon.player.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comeon.player.dto.LoginRequest;
import com.comeon.player.dto.PlayerFeedRequest;
import com.comeon.player.dto.PlayerListResponse;
import com.comeon.player.dto.PlayerRegisterRequest;
import com.comeon.player.dto.ResponseList;
import com.comeon.player.dto.SessionResponse;
import com.comeon.player.dto.TimeLimitRequest;
import com.comeon.player.entity.Player;
import com.comeon.player.entity.PlayerSession;
import com.comeon.player.repository.PlayerRepository;
import com.comeon.player.repository.PlayerSessionRepository;
import com.comeon.player.service.PlayerService;

@RestController
@RequestMapping("/api/player")
@CrossOrigin(origins = "*") 
public class PlayerController {


    @Autowired
    private PlayerService playerService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody PlayerRegisterRequest request) {
        playerService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<SessionResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(playerService.login(request));
    }

    @PostMapping("/logout/{sessionId}")
    public ResponseEntity<Void> logout(@PathVariable Long sessionId) {
        playerService.logout(sessionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/setTimeLimit")
    public ResponseEntity<Void> setLimit(@RequestBody TimeLimitRequest request) {
        playerService.setTimeLimit(request);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/feed")
    public ResponseEntity<String> feedData(@RequestBody PlayerFeedRequest request) {
        int count = playerService.feedPlayers(request);
        return ResponseEntity.ok(count + " players created successfully.");
    }
    
    @GetMapping("/")
    public ResponseEntity<ResponseList> userList() {	
        return ResponseEntity.ok(playerService.playerList());
    }
    
}
