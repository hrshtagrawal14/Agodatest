package com.challenge.agoda.responses;

import java.util.List;

import com.challenge.agoda.enums.CurrentStateEnum;

public class StateSummary {

	private CurrentStateEnum state;
	
	private int count;
	
	private List<IssueResponse> issues;

	public CurrentStateEnum getState() {
		return state;
	}

	public void setState(CurrentStateEnum state) {
		this.state = state;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<IssueResponse> getIssues() {
		return issues;
	}

	public void setIssues(List<IssueResponse> issues) {
		this.issues = issues;
	}

	public StateSummary() {
		super();
	}	
}
