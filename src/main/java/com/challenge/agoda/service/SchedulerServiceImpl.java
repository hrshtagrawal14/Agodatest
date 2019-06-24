package com.challenge.agoda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.agoda.exception.InvalidInputException;
import com.challenge.agoda.model.InputRequest;

// Sync Service Implementation class for Bug Tracking System.
@Service
public class SchedulerServiceImpl implements SchedulerService {

	@Autowired
	private ThirdPartyService thirdPartyService;

	private Thread syncThread = null;

	@Override
	public void syncAndUpdate(final InputRequest inputRequest) throws InvalidInputException {
		thirdPartyService.populateProjects(inputRequest);
		invokeThirdPartyService();
	}

	private void invokeThirdPartyService() {
		if (syncThread == null) {
			synchronized (SchedulerServiceImpl.class) {
				if (syncThread == null) {
					syncThread = new Thread(thirdPartyService);
					syncThread.start();
				}
			}
		}
	}

}
