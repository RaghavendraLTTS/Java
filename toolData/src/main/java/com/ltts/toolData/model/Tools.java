package com.ltts.toolData.model;

import javax.persistence.*;

@Entity
@Table(name = "tools")
public class Tools {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Projects getProject() {
		return project;
	}

	public void setProject(Projects project) {
		this.project = project;
	}

	private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Projects project;

    // Getters and Setters
}