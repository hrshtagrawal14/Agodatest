package com.challenge.agoda.service;

import org.springframework.stereotype.Component;

import com.challenge.agoda.exception.InvalidInputException;
import com.challenge.agoda.model.InputRequest;

@Component
public interface SchedulerService {
	
	void syncAndUpdate(InputRequest inputRequest) throws InvalidInputException;
	
}


