package com.comeon.player.dto;

import java.time.LocalDateTime;


public class PlayerSessionListResponse {
	public PlayerSessionListResponse(Long id, Long player, LocalDateTime loginTime, LocalDateTime logoutTime) {
		super();
		this.id = id;
		this.player = player;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
	}
	private Long id;
    private Long player;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPlayer() {
		return player;
	}
	public void setPlayer(Long player) {
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
