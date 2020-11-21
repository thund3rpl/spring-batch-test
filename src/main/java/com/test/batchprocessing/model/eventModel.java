package com.test.batchprocessing.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class eventModel {

	@Id
	private String id;
	private String state;
	private String host;
	private String type;
	private long timestamp;
	private boolean alert=false;
	
	public eventModel() {
		super();
	}
	
	
	public eventModel(String id,String state,String host,String type,long timestamp) {
		this.id=id;
		this.state=state;
		this.host=host;
		this.type=type;
		this.timestamp=timestamp;
	}
	

	public void setId(String id) {
		this.id=id;
	}

	public String getId() {
		return id;
	}


	public void setState(String state) {
		this.state=state;
	}

	public String getState() {
		return state;
	}
	
	
	public void setHost(String host) {
		this.host=host;
	}

	public String getHost() {
		return host;
	}
	
	
	public void setType(String type) {
		this.type=type;
	}

	public String getType() {
		return type;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp=timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	public void setAlert(boolean alert) {
		this.alert=alert;
	}

	public boolean getAlert() {
		return alert;
	}
	
	
	@Override
	public String toString() {
		return "id: " + id + ", state: " + state + ", host: " + host + ", type:" + type + ",timestamp: " + timestamp + " alert: " + alert;
	}
	


}
