package com.comeon.player.dto;

public class TimeLimitRequest {
	 public Long playerId;
	 public Long dailyLimitInSeconds;
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public Long getDailyLimitInSeconds() {
		return dailyLimitInSeconds;
	}
	public void setDailyLimitInSeconds(Long dailyLimitInSeconds) {
		this.dailyLimitInSeconds = dailyLimitInSeconds;
	}
	 
	 
	    

}
