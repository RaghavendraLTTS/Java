package com.toolDataToDb.Model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "process_data")
public class ProcessData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer transactionid;

    @Column(columnDefinition = "json")
    private String data;

    private String processInstanceid;

    private LocalDateTime timestamp;

    private String toolname;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTransactionId() {
		return transactionid;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionid = transactionId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getProcessInstanceId() {
		return processInstanceid;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceid = processInstanceId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	@PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

	public String getToolname() {
		return toolname;
	}

	public void setToolname(String toolname) {
		this.toolname = toolname;
	}

}