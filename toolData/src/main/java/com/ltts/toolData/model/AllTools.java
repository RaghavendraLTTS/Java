package com.ltts.toolData.model;

import javax.persistence.*;

@Entity
@Table(name = "tools")
public class AllTools {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private String name;
    private String value;
    private int project_Id;

    public int getProject_Id() {
		return project_Id;
	}
	public void setProject_Id(int project_Id) {
		this.project_Id = project_Id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}


}