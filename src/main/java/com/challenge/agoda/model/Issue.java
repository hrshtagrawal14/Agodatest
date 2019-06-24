package com.challenge.agoda.model;

import java.util.List;

import com.challenge.agoda.enums.CurrentStateEnum;
import com.challenge.agoda.enums.IssueTypeEnum;

public class Issue {

	private String issue_id;
	
	private IssueTypeEnum type;
	
	private CurrentStateEnum current_state;
	
	private List<ChangeLog> changelogs;
	
	public Issue() {}

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

	public CurrentStateEnum getCurrent_state() {
		return current_state;
	}

	public void setCurrent_state(CurrentStateEnum current_state) {
		this.current_state = current_state;
	}

	public List<ChangeLog> getChangelogs() {
		return changelogs;
	}

	public void setChangelogs(List<ChangeLog> changelogs) {
		this.changelogs = changelogs;
	}

}
