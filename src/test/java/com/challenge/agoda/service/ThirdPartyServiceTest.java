package com.challenge.agoda.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.challenge.agoda.enums.CurrentStateEnum;
import com.challenge.agoda.enums.IssueTypeEnum;
import com.challenge.agoda.exception.InvalidInputException;
import com.challenge.agoda.model.InputRequest;
import com.challenge.agoda.responses.GetWeeklySummaryResponse;
import com.challenge.agoda.responses.WeeklySummary;

public class ThirdPartyServiceTest {

	private InputRequest inputRequest;

	private InputRequest badRequest;

	private List<IssueTypeEnum> types;

	private List<CurrentStateEnum> states;

	private ThirdPartyService thirdPartyService;

	@Before
	public void setup() {
		types = new ArrayList<>();
		types.add(IssueTypeEnum.bug);
		states = new ArrayList<>();
		states.add(CurrentStateEnum.open);
		inputRequest = new InputRequest("project1", "2019W01", "2019W03", types, states);
		badRequest = new InputRequest("project1", "2019W03", "2019W01", types, states);
		thirdPartyService = new ThirdPartyService();
	}

	@Test
	public void testSuccessfulPopulateProjects() throws InvalidInputException {
		thirdPartyService.populateProjects(inputRequest);
	}

	@Test(expected = InvalidInputException.class)
	public void testPopulateProjects_NullRequest() throws InvalidInputException {
		thirdPartyService.populateProjects(null);
	}

	@Test(expected = InvalidInputException.class)
	public void testPopulateProjects_InvalidRequest() throws InvalidInputException {
		thirdPartyService.populateProjects(badRequest);
	}

	@Test
	public void testSuccessfulGetWeeklySummaryFromDataStore() throws InvalidInputException {
		thirdPartyService.populateProjects(inputRequest);
		GetWeeklySummaryResponse response = thirdPartyService.getWeeklySummaryFromDataStore();
		assertNotNull(response);
		assertEquals("project1", response.getProject_id());
		List<WeeklySummary> weeklySummaries = response.getWeekly_summaries();
		assertEquals("2019W01", weeklySummaries.get(0).getWeek());
		assertEquals("2019W02", weeklySummaries.get(1).getWeek());
		assertEquals("2019W03", weeklySummaries.get(2).getWeek());
	}

}
