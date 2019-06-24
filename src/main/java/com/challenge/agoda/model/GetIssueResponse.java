package com.challenge.agoda.model;

import java.util.List;

public class GetIssueResponse {

	private String project_id;
	
	private List<Issue> issues;
	
	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public GetIssueResponse() {}
}
