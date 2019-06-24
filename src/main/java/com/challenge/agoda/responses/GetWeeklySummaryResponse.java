package com.challenge.agoda.responses;

import java.util.List;

public class GetWeeklySummaryResponse {

	private String project_id;

	private List<WeeklySummary> weekly_summaries;

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public List<WeeklySummary> getWeekly_summaries() {
		return weekly_summaries;
	}

	public void setWeekly_summaries(List<WeeklySummary> weekly_summaries) {
		this.weekly_summaries = weekly_summaries;
	}

	public GetWeeklySummaryResponse() {
		super();
	}
}