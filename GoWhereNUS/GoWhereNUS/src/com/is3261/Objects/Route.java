package com.is3261.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Route {

	String start;
	String end;
	String steps;
	String id; // the primary key in database

	boolean isHealthy;
	boolean isRainShelter;
	boolean isLazy;
	
	public boolean isLazy() {
		return isLazy;
	}


	public void setLazy(boolean isLazy) {
		this.isLazy = isLazy;
	}


	/*
	 * Sample format for steps (JSON):
	{"steps":[{"description":"Get out lecture hall and turn left","time":1},{"description":"turn left, go downstairs","time":1}]}
	
	*
	*/
	public Route() {

	}
	

	public boolean isHealthy() {
		return isHealthy;
	}

	public void setHealthy(boolean isHealthy) {
		this.isHealthy = isHealthy;
	}

	public boolean isRainShelter() {
		return isRainShelter;
	}

	public void setRainShelter(boolean isRainShelter) {
		this.isRainShelter = isRainShelter;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public float getTotaltime() throws JSONException {
		
		JSONObject stepsJSON = new JSONObject(this.steps);
		JSONArray stepsArray = stepsJSON.getJSONArray("steps");
		
		float totaltime = 0;
		
		for (int i=0; i<stepsArray.length();i++){
			JSONObject step = stepsArray.getJSONObject(i);
			int time = step.getInt("time");
			totaltime +=  time;
		}
		
		return totaltime; 
	}



	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
}
