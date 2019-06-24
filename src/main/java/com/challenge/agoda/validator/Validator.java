package com.challenge.agoda.validator;

import org.springframework.stereotype.Component;

import com.challenge.agoda.enums.IssueTypeEnum;
import com.challenge.agoda.exception.InvalidInputException;
import com.challenge.agoda.model.InputRequest;
import com.challenge.agoda.util.Util;

// Validator class for Bug tracking system that validates input request and throws custom Invalid Input Exception,
// if the request is invalid.
@Component
public class Validator {

	public static void validateInput(final InputRequest inputRequest) throws InvalidInputException {
		if (inputRequest == null) {
			throw new InvalidInputException("Input Request cannot be null");
		}
		if (!Util.isWeekInDesiredFormat(inputRequest.getFrom_week())
				|| !Util.isWeekInDesiredFormat(inputRequest.getTo_week())) {
			throw new InvalidInputException("Input week is not in correct format");
		}
		String fromWeek = inputRequest.getFrom_week();
		String toWeek = inputRequest.getTo_week();
		int startWeek = Integer.parseInt(fromWeek.substring(5));
		int endWeek = Integer.parseInt(toWeek.substring(5));
		if (startWeek > endWeek) {
			throw new InvalidInputException("FromWeek should be lesser than or equal to ToWeek");
		}
	}
}
