package com.comeon.player.dto;

import java.util.List;

public class PlayerFeedRequest {
	 private List<PlayerRegisterRequest> players;

	public List<PlayerRegisterRequest> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerRegisterRequest> players) {
		this.players = players;
	}
	 
	 
}
