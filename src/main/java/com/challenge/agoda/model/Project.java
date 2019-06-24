package com.challenge.agoda.model;

import com.challenge.agoda.enums.CurrentStateEnum;
import com.challenge.agoda.enums.IssueTypeEnum;

public class Project {
	
	private String projectId;
	
	private IssueTypeEnum type;
	
	private CurrentStateEnum state;
	
	private String week;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public IssueTypeEnum getType() {
		return type;
	}

	public void setType(IssueTypeEnum type) {
		this.type = type;
	}

	public CurrentStateEnum getState() {
		return state;
	}

	public void setState(CurrentStateEnum state) {
		this.state = state;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((week == null) ? 0 : week.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		if (state != other.state)
			return false;
		if (type != other.type)
			return false;
		if (week == null) {
			if (other.week != null)
				return false;
		} else if (!week.equals(other.week))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", type=" + type + ", state=" + state + ", week=" + week + "]";
	}

	public Project(String projectId, IssueTypeEnum type, CurrentStateEnum state, String week) {
		super();
		this.projectId = projectId;
		this.type = type;
		this.state = state;
		this.week = week;
	}	
}