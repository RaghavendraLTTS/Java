package com.ltts.toolData.model;

import java.util.ArrayList;

import com.ltts.toolData.Util.Utility;

public class ToolConfigToComundaReq{
	
    public String clientName;
    public String projectName;
    public ArrayList<String> tools;
    public Boolean isTimerSet;
    public String schedulerType;
	public String scheduledTime;
    public String timeDuration;
    public String timeCycle;
    
    
    public String getSchedulerType() {
		return schedulerType;
	}
	public void setSchedulerType(String schedulerType) {
		this.schedulerType = schedulerType;
	}
    
    public Boolean getIsTimerSet() {
		return isTimerSet;
	}
	public void setIsTimerSet(Boolean isTimerSet) {
		this.isTimerSet = isTimerSet;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getScheduledTime() {
		return scheduledTime;
	}
	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	public String getTimeDuration() {
		return timeDuration;
	}
	public void setTimeDuration(String timeDuration) {
		this.timeDuration = timeDuration;
	}
	public String getTimeCycle() {
		return timeCycle;
	}
	public void setTimeCycle(String timeCycle) {
		this.timeCycle = timeCycle;
	}
	public ArrayList<String> getTools() {
		return tools;
	}
	public void setTools(ArrayList<String> tools) {
		this.tools = tools;
	}
	
}
