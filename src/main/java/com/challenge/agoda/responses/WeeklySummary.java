package com.challenge.agoda.responses;

import java.util.List;

import com.challenge.agoda.enums.CurrentStateEnum;

public class WeeklySummary {

	private String week;

	private List<StateSummary> state_summaries;

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public List<StateSummary> getState_summaries() {
		return state_summaries;
	}

	public void setState_summaries(List<StateSummary> state_summaries) {
		this.state_summaries = state_summaries;
	}

	public WeeklySummary() {
		super();
	}

}