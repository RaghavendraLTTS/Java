package com.ltts.usermanagement.UMmodel;

import java.time.LocalDateTime;

public class SessionResponse {
    private Long id;
    private Long userId;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private Integer duration;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	 // in minutes

    // getters and setters
}