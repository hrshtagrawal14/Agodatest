package com.challenge.agoda.model;

import java.util.List;

import com.challenge.agoda.enums.CurrentStateEnum;
import com.challenge.agoda.enums.IssueTypeEnum;

public class InputRequest {
	
	private String project_id;
	
	private String from_week;
	
	private String to_week;
	
	private List<IssueTypeEnum> types;
	
	private List<CurrentStateEnum> states;	
	
	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getFrom_week() {
		return from_week;
	}

	public void setFrom_week(String from_week) {
		this.from_week = from_week;
	}

	public String getTo_week() {
		return to_week;
	}

	public void setTo_week(String to_week) {
		this.to_week = to_week;
	}

	public List<IssueTypeEnum> getTypes() {
		return types;
	}

	public void setTypes(List<IssueTypeEnum> types) {
		this.types = types;
	}

	public List<CurrentStateEnum> getStates() {
		return states;
	}

	public void setStates(List<CurrentStateEnum> states) {
		this.states = states;
	}

	public InputRequest(String project_id, String from_week, String to_week, List<IssueTypeEnum> types,
			List<CurrentStateEnum> states) {
		super();
		this.project_id = project_id;
		this.from_week = from_week;
		this.to_week = to_week;
		this.types = types;
		this.states = states;
	}

	public InputRequest() {}
}
