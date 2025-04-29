package com.comeon.player.entity;

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PlayerSession {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "player_id", nullable = false)
	    private Player player;

	    private LocalDateTime loginTime;
	    private LocalDateTime logoutTime;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Player getPlayer() {
			return player;
		}
		public void setPlayer(Player player) {
			this.player = player;
		}
		public LocalDateTime getLoginTime() {
			return loginTime;
		}
		public void setLoginTime(LocalDateTime loginTime) {
			this.loginTime = loginTime;
		}
		public LocalDateTime getLogoutTime() {
			return logoutTime;
		}
		public void setLogoutTime(LocalDateTime logoutTime) {
			this.logoutTime = logoutTime;
		}
	
	    
    
}
