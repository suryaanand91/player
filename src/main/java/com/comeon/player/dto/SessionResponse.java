package com.comeon.player.dto;

public class SessionResponse {
	public SessionResponse(Long sessionId, String message) {
		super();
		this.sessionId = sessionId;
		this.message = message;
	}
	public SessionResponse(Long sessionId) {
		super();
		this.sessionId = sessionId;
	}
	private Long sessionId;
    private String message;
	public Long getSessionId() {
		return sessionId;
	}
	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    

}
