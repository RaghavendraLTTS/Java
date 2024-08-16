package com.ltts.toolData.model;

import java.util.List;

public class CompositeRequest {

    private String clientName;
    public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientValue() {
		return clientValue;
	}

	public void setClientValue(String clientValue) {
		this.clientValue = clientValue;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectValue() {
		return projectValue;
	}

	public void setProjectValue(String projectValue) {
		this.projectValue = projectValue;
	}

	public List<ToolRequest> getTools() {
		return tools;
	}

	public void setTools(List<ToolRequest> tools) {
		this.tools = tools;
	}

	private String clientValue;
    
    private String projectName;
    private String projectValue;
    
    private List<ToolRequest> tools;

    // Getters and Setters

    public static class ToolRequest {
        public String getToolName() {
			return toolName;
		}
		public void setToolName(String toolName) {
			this.toolName = toolName;
		}
		public String getToolValue() {
			return toolValue;
		}
		public void setToolValue(String toolValue) {
			this.toolValue = toolValue;
		}
		private String toolName;
        private String toolValue;

        // Getters and Setters
    }
}