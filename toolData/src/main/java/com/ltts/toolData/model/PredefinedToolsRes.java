package com.ltts.toolData.model;

import java.util.List;

public class PredefinedToolsRes {

	private String toolname;
    private List<String> predefinedtools;

    public PredefinedToolsRes(String toolname, List<String> predefinedtools) {
        this.toolname = toolname;
        this.predefinedtools = predefinedtools;
    }

    // Getters
    public String getToolname() {
        return toolname;
    }

    public List<String> getPredefinedtools() {
        return predefinedtools;
    }

    // Setters
    public void setToolname(String toolname) {
        this.toolname = toolname;
    }

    public void setPredefinedtools(List<String> predefinedtools) {
        this.predefinedtools = predefinedtools;
    }
}