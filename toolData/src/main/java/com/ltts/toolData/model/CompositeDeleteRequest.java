package com.ltts.toolData.model;

import java.util.List;

public class CompositeDeleteRequest {

    private Integer clientId;
    public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public List<Integer> getToolIds() {
		return toolIds;
	}
	public void setToolIds(List<Integer> toolIds) {
		this.toolIds = toolIds;
	}
	private Integer projectId;
    private List<Integer> toolIds;

    // Getters and Setters
}