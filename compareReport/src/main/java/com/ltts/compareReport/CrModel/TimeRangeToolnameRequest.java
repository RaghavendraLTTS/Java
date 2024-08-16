package com.ltts.compareReport.CrModel;

import java.time.LocalDateTime;

public class TimeRangeToolnameRequest {
	private String toolname;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public String getToolname() {
		return toolname;
	}

	public void setToolname(String toolname) {
		this.toolname = toolname;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	// Getters and setters

}
