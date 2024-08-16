package com.ltts.toolData.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Projects")
public class Projects {

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

	public Clients getClient() {
		return client;
	}

	public void setClient(Clients client) {
		this.client = client;
	}

	public List<Tools> getTools() {
		return tools;
	}

	public void setTools(List<Tools> tools) {
		this.tools = tools;
	}

	private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Clients client;

    @OneToMany(mappedBy = "project")
    private List<Tools> tools;

    // Getters and Setters
}