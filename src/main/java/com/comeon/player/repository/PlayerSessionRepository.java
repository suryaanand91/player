package com.comeon.player.repository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.comeon.player.entity.PlayerSession;

public interface PlayerSessionRepository extends JpaRepository<PlayerSession, Long> {
	List<PlayerSession> findByPlayerIdAndLogoutTimeIsNull(Long playerId);
    List<PlayerSession> findByPlayerIdAndLoginTimeBetween(Long playerId, LocalDateTime start, LocalDateTime end);
    List<PlayerSession> findAllByLogoutTimeIsNull();

}
