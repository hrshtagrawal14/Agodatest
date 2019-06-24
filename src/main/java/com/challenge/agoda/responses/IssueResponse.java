package com.challenge.agoda.responses;

import com.challenge.agoda.enums.IssueTypeEnum;

public class IssueResponse {

	private String issue_id;

	private IssueTypeEnum type;


	public String getIssue_id() {
		return issue_id;
	}

	public void setIssue_id(String issue_id) {
		this.issue_id = issue_id;
	}

	public IssueTypeEnum getType() {
		return type;
	}

	public void setType(IssueTypeEnum type) {
		this.type = type;
	}

	public IssueResponse(String issue_id, IssueTypeEnum type) {
		super();
		this.issue_id = issue_id;
		this.type = type;
	}
	
}
