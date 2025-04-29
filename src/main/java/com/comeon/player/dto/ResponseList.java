package com.comeon.player.dto;

import java.util.List;

public class ResponseList {
	
	List<PlayerListResponse> players;
	List<PlayerSessionListResponse> sessions;
	public List<PlayerListResponse> getPlayers() {
		return players;
	}
	public void setPlayers(List<PlayerListResponse> players) {
		this.players = players;
	}
	public List<PlayerSessionListResponse> getSessions() {
		return sessions;
	}
	public void setSessions(List<PlayerSessionListResponse> sessions) {
		this.sessions = sessions;
	}
	
	

}
