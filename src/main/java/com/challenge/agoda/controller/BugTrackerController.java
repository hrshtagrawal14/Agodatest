package com.challenge.agoda.controller;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.agoda.exception.InvalidInputException;
import com.challenge.agoda.model.InputRequest;
import com.challenge.agoda.responses.GetWeeklySummaryResponse;
import com.challenge.agoda.service.SchedulerService;
import com.challenge.agoda.service.ThirdPartyService;
import com.challenge.agoda.validator.Validator;

@RestController
public class BugTrackerController {

	private static final Logger logger = LoggerFactory.getLogger(BugTrackerController.class);

	@Autowired
	private SchedulerService schedulerService;

	@Autowired
	private ThirdPartyService thirdPartyService;

	@Autowired
	public BugTrackerController(final SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	/**
	 * REST endpoint for get weekly summary
	 *
	 * @param InputRequest
	 * @return ResponseEntity<GetWeeklySummaryResponse>
	 */
	@GetMapping("/getWeeklySummary")
	public ResponseEntity<GetWeeklySummaryResponse> getWeeklySummary(@RequestBody @NotNull InputRequest inputRequest) {
		GetWeeklySummaryResponse response = null;
		try {
			Validator.validateInput(inputRequest);
			schedulerService.syncAndUpdate(inputRequest);
			response = thirdPartyService.getWeeklySummaryFromDataStore();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (InvalidInputException ex) {
			logger.error("Invalid Input Exception - Error in the input request.", ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			logger.error("Common Exception - Error in getting the weekly summary.", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
