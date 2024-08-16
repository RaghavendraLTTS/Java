package com.toolDataToDb.Model;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.JsonNode;

public class ToolResponse {
	private Long id;
    private String processInstanceID;
    private String toolName;
    private JsonNode data;
    private LocalDateTime timestamp;

    public ToolResponse(Long id, String processInstanceID, String toolName, JsonNode data, LocalDateTime timestamp) {
        this.id = id;
        this.processInstanceID = processInstanceID;
        this.toolName = toolName;
        this.data = data;
        this.timestamp = timestamp;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessInstanceID() {
        return processInstanceID;
    }

    public void setProcessInstanceID(String processInstanceID) {
        this.processInstanceID = processInstanceID;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}