package com.ltts.toolData.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "toolconfig")
public class PredefinedTools {

    @Id
    private String toolname;
    private String predefinedtools;

    // Getters and setters
    public String getToolname() {
        return toolname;
    }

    public void setToolname(String toolname) {
        this.toolname = toolname;
    }

    public String getPredefinedtools() {
        return predefinedtools;
    }

    public void setPredefinedtools(String predefinedtools) {
        this.predefinedtools = predefinedtools;
    }
}

