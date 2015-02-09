package com.is3261.Objects;

import java.io.Serializable;

public class User implements Serializable {
	
	public User(){
		
	}
	
	private String objectId; // objectId in Parse
	
	private String username;

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
