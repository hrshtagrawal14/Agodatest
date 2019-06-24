package com.challenge.agoda.model;

import com.challenge.agoda.enums.CurrentStateEnum;

public class ChangeLog {
	
	private String changed_on;
	
	private CurrentStateEnum from_state;
	
	private CurrentStateEnum to_state;
	
	public String getChanged_on() {
		return changed_on;
	}

	public void setChanged_on(String changed_on) {
		this.changed_on = changed_on;
	}

	public CurrentStateEnum getFrom_state() {
		return from_state;
	}

	public void setFrom_state(CurrentStateEnum from_state) {
		this.from_state = from_state;
	}

	public CurrentStateEnum getTo_state() {
		return to_state;
	}

	public void setTo_state(CurrentStateEnum to_state) {
		this.to_state = to_state;
	}

	public ChangeLog() {}
}
