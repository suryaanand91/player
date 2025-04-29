package com.comeon.player.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.comeon.player.dto.LoginRequest;
import com.comeon.player.dto.PlayerFeedRequest;
import com.comeon.player.dto.PlayerListResponse;
import com.comeon.player.dto.PlayerRegisterRequest;
import com.comeon.player.dto.PlayerSessionListResponse;
import com.comeon.player.dto.ResponseList;
import com.comeon.player.dto.SessionResponse;
import com.comeon.player.dto.TimeLimitRequest;
import com.comeon.player.entity.Player;
import com.comeon.player.entity.PlayerSession;
import com.comeon.player.repository.PlayerRepository;
import com.comeon.player.repository.PlayerSessionRepository;

import jakarta.transaction.Transactional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepo;
    @Autowired 
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PlayerSessionRepository sessionRepo;
    
    public  void register(PlayerRegisterRequest request) {
        // Check if email already exists
        if (playerRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered.");
        }

        // Hash password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Create and save Player entity
        Player player = new Player();
        player.setEmail(request.getEmail());
        player.setPassword(encodedPassword);
        player.setName(request.getName());
        player.setSurname(request.getSurname());
        player.setDateOfBirth(request.getDateOfBirth());
        player.setAddress(request.getAddress());

        playerRepo.save(player);
    
    }

    public SessionResponse login(LoginRequest req) {
        Player p = playerRepo.findByEmail(req.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!new BCryptPasswordEncoder().matches(req.getPassword(), p.getPassword()))
            throw new RuntimeException("Invalid credentials");

        if (p.getDailyLimitInSeconds() != null) {
            LocalDateTime todayStart = LocalDate.now().atStartOfDay();
            List<PlayerSession> todaySessions = sessionRepo.findByPlayerIdAndLoginTimeBetween(
                p.getId(), todayStart, LocalDateTime.now()
            );

            long totalSeconds = todaySessions.stream().mapToLong(s -> {
                if (s.getLogoutTime() != null)
                    return Duration.between(s.getLoginTime(), s.getLogoutTime()).getSeconds();
                return Duration.between(s.getLoginTime(), LocalDateTime.now()).getSeconds();
            }).sum();

            if (totalSeconds >= p.getDailyLimitInSeconds())
                throw new RuntimeException("Time limit reached");
        }

        PlayerSession session = new PlayerSession();
        session.setPlayer(p);
        session.setLoginTime(LocalDateTime.now());
        sessionRepo.save(session);
        return new SessionResponse(session.getId());
    }

    public void setTimeLimit(TimeLimitRequest req) {
        List<PlayerSession> active = sessionRepo.findByPlayerIdAndLogoutTimeIsNull(req.playerId);
        if (active.isEmpty())
            throw new RuntimeException("Player must be active to set time limit");

        Player p = playerRepo.findById(req.playerId)
            .orElseThrow(() -> new RuntimeException("Player not found"));
        p.setDailyLimitInSeconds(req.dailyLimitInSeconds);
        playerRepo.save(p);
    }
    
    public void logout(long sessionId) {
        PlayerSession s = sessionRepo.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Session not found"));
        s.setLogoutTime(LocalDateTime.now());
        sessionRepo.save(s);
    }
    
    @Transactional
    public void forceLogoutIfLimitExceeded() {
        // Get all active sessions
        List<PlayerSession> activeSessions = sessionRepo.findAllByLogoutTimeIsNull();

        for (PlayerSession session : activeSessions) {
            Player player = session.getPlayer();
            Duration sessionDuration = Duration.between(session.getLoginTime(), LocalDateTime.now());

            if (player.getDailyLimitInSeconds() != null &&
                sessionDuration.toMinutes() >= player.getDailyLimitInSeconds()) {

                // End session
                session.setLogoutTime(LocalDateTime.now());
                sessionRepo.save(session);
            }
        }
    }

    
    public int feedPlayers(PlayerFeedRequest request) {
        int count = 0;
        for (PlayerRegisterRequest playerData : request.getPlayers()) {
        	 if (playerRepo.findByEmail(playerData.getEmail()).isPresent()) {
                // throw new IllegalArgumentException("Email already registered.");
        		 continue;
             }
            Player player = new Player();
            player.setEmail(playerData.getEmail());
            player.setPassword(passwordEncoder.encode(playerData.getPassword()));
            player.setName(playerData.getName());
            player.setSurname(playerData.getSurname());
            player.setDateOfBirth(playerData.getDateOfBirth());
            player.setAddress(playerData.getAddress());
            playerRepo.save(player);
            count++;
        }
        return count;
    }
    
    
    public ResponseList playerList(){
    	 ResponseList response =new ResponseList();
    	List<Player> players=playerRepo.findAll();
    	List<PlayerListResponse> playersList =  players.stream().map(player -> new PlayerListResponse(
                player.getId(),
                player.getEmail(),
                player.getName(),
                player.getSurname()))
            .collect(Collectors.toList());
    	response.setPlayers(playersList);
    	List<PlayerSession> playerSessions=sessionRepo.findAll();
    	List<PlayerSessionListResponse> sessionList =  playerSessions.stream().map(s -> new PlayerSessionListResponse(
                s.getId(),
                s.getPlayer().getId(),
                s.getLoginTime(),
                s.getLogoutTime()))
            .collect(Collectors.toList());
    	response.setSessions(sessionList);
    	return response;
    }
}
