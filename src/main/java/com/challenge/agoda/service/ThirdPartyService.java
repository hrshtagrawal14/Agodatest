package com.challenge.agoda.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.challenge.agoda.model.ChangeLog;
import com.challenge.agoda.model.GetIssueResponse;
import com.challenge.agoda.model.InputRequest;
import com.challenge.agoda.model.Issue;
import com.challenge.agoda.model.Project;
import com.challenge.agoda.responses.GetWeeklySummaryResponse;
import com.challenge.agoda.responses.IssueResponse;
import com.challenge.agoda.responses.StateSummary;
import com.challenge.agoda.responses.WeeklySummary;
import com.challenge.agoda.util.Util;
import com.challenge.agoda.validator.Validator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.challenge.agoda.enums.CurrentStateEnum;
import com.challenge.agoda.enums.IssueTypeEnum;
import com.challenge.agoda.exception.InvalidInputException;

// Third Party Service class that communicates with external system and updates local data store.
// Also, it returns get weekly summary response from local data store.
@Service
public class ThirdPartyService implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(ThirdPartyService.class);

	@Value("${time.mills.interval}")
	private int syncIntervalInMillis;

	private final static String projectsBasePath = "config/json/";

	private final static String jsonExtension = ".json";

	private InputRequest inputRequest;

	private ConcurrentHashMap<String, Integer> newProjectsMap;

	private ConcurrentHashMap<String, Integer> oldProjectsMap;

	private ConcurrentHashMap<Project, Set<String>> dataStore;

	public ThirdPartyService() {
		newProjectsMap = new ConcurrentHashMap<>();
		oldProjectsMap = new ConcurrentHashMap<>();
		dataStore = new ConcurrentHashMap<>();
	}

	public void setInputRequest(InputRequest inputRequest) {
		this.inputRequest = inputRequest;
	}

	@Override
	public void run() {
		try {
			pollGetIssues();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	public void populateProjects(final InputRequest inputRequest) throws InvalidInputException {
		Validator.validateInput(inputRequest);
		setInputRequest(inputRequest);
		String projectId = inputRequest.getProject_id();
		if (oldProjectsMap.containsKey(projectId)) {
			oldProjectsMap.put(projectId, oldProjectsMap.getOrDefault(projectId, 0) + 1);
		} else {
			newProjectsMap.put(projectId, newProjectsMap.getOrDefault(projectId, 0) + 1);
		}
	}

	private void pollGetIssues() throws JsonParseException, JsonMappingException, IOException, ParseException {
		while (true) {
			try {
				String nextProjectToSync = getNextProjectToSync();
				if (nextProjectToSync != null) {
					populateDataStoreWithProject(nextProjectToSync);
				}
				Thread.sleep(syncIntervalInMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String getNextProjectToSync() {
		String nextProjectToSync = getMaximumFrequencyProject(newProjectsMap);
		if (nextProjectToSync != null) {
			newProjectsMap.remove(nextProjectToSync);
		} else {
			nextProjectToSync = getMaximumFrequencyProject(oldProjectsMap);
			oldProjectsMap.remove(nextProjectToSync);
		}
		oldProjectsMap.put(nextProjectToSync, 1);
		return nextProjectToSync;
	}

	private String getMaximumFrequencyProject(Map<String, Integer> projectsMap) {
		String nextProjectToSync = null;
		int projectFrequencyCounter = 0;
		for (Map.Entry<String, Integer> entry : projectsMap.entrySet()) {
			if (entry.getValue() > projectFrequencyCounter) {
				nextProjectToSync = entry.getKey();
				projectFrequencyCounter = entry.getValue();
			}
		}
		return nextProjectToSync;
	}

	public GetWeeklySummaryResponse getWeeklySummaryFromDataStore() {
		GetWeeklySummaryResponse response = new GetWeeklySummaryResponse();
		String projectId = inputRequest.getProject_id();
		String fromWeek = inputRequest.getFrom_week();
		String toWeek = inputRequest.getTo_week();
		int firstWeek = Integer.parseInt(fromWeek.substring(fromWeek.length() - 2));
		int lastWeek = Integer.parseInt(toWeek.substring(toWeek.length() - 2));
		String baseWeek = fromWeek.substring(0, fromWeek.length() - 2);
		List<WeeklySummary> weeklySummaries = new ArrayList<>();
		for (int week = 0; week <= (lastWeek - firstWeek); week++) {
			WeeklySummary weeklySummary = new WeeklySummary();
			int nxtWeek = firstWeek + week;
			String next = Integer.toString(nxtWeek);
			if (nxtWeek < 10) {
				next = Util.ZERO_PREFIX + next;
			}
			String nextWeek = baseWeek + next;
			weeklySummary.setWeek(nextWeek);
			List<StateSummary> stateSummaries = new ArrayList<>();
			for (CurrentStateEnum currentState : inputRequest.getStates()) {
				StateSummary stateSummary = new StateSummary();
				stateSummary.setState(currentState);
				for (IssueTypeEnum issueType : inputRequest.getTypes()) {
					Project project = new Project(projectId, issueType, currentState, nextWeek);
					if (dataStore.containsKey(project)) {
						Set<String> issues = dataStore.get(project);
						List<IssueResponse> issueResponses = new ArrayList<>();
						for (String issue : issues) {
							IssueResponse issueResponse = new IssueResponse(issue, issueType);
							issueResponses.add(issueResponse);
						}
						stateSummary.setCount(issues.size());
						stateSummary.setIssues(issueResponses);
					}
				}
				if (stateSummary.getCount() > 0) {
					stateSummaries.add(stateSummary);
				}
				weeklySummary.setState_summaries(stateSummaries);
			}
			weeklySummaries.add(weeklySummary);
		}
		response.setProject_id(projectId);
		response.setWeekly_summaries(weeklySummaries);
		return response;
	}

	private void populateDataStoreWithProject(final String projectId)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		ObjectMapper objectMapper = new ObjectMapper();
		String projectPath = projectsBasePath + projectId + jsonExtension;
		File resource = new ClassPathResource(projectPath).getFile();
		if (!resource.exists()) {
			logger.debug("Input file for the project " + projectId + " doesn't exists");
			return;
		}
		GetIssueResponse response = objectMapper.readValue(resource, GetIssueResponse.class);

		for (Issue issue : response.getIssues()) {
			for (ChangeLog changeLog : issue.getChangelogs()) {
				Project project = new Project(response.getProject_id(), issue.getType(), issue.getCurrent_state(),
						Util.getDate(changeLog.getChanged_on()));
				dataStore.putIfAbsent(project, new HashSet<>());
				dataStore.get(project).add(issue.getIssue_id());
			}
		}
	}
}
