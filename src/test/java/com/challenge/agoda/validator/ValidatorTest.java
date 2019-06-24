package com.challenge.agoda.validator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.challenge.agoda.enums.CurrentStateEnum;
import com.challenge.agoda.enums.IssueTypeEnum;
import com.challenge.agoda.exception.InvalidInputException;
import com.challenge.agoda.model.InputRequest;

public class ValidatorTest {

	private InputRequest inputRequest;
	
	private InputRequest badRequest1, badRequest2;
	
	private List<IssueTypeEnum> types;
	
	private List<CurrentStateEnum> states;	
	
	@Before
	public void setup() {
		types = new ArrayList<>();
		types.add(IssueTypeEnum.bug);
		states = new ArrayList<>();
		states.add(CurrentStateEnum.open);
		inputRequest = new InputRequest("project1", "2019W01", "2019W03", types, states);
		badRequest1 = new InputRequest("project1", "2019W03", "2019W01", types, states);
		badRequest2 = new InputRequest("project1", "2019W00", "2019W03", types, states);
	}
	
	@Test
	public void testSuccessfulValidateInput() throws InvalidInputException {
		Validator.validateInput(inputRequest);
	}

	@Test(expected=InvalidInputException.class)
	public void testValidateInput_NullRequest() throws InvalidInputException {
		Validator.validateInput(null);
	}

	@Test(expected=InvalidInputException.class)
	public void testValidateInput_BadRequest1() throws InvalidInputException {
		Validator.validateInput(badRequest1);
	}

	@Test(expected=InvalidInputException.class)
	public void testValidateInput_BadRequest2() throws InvalidInputException {
		Validator.validateInput(badRequest2);
	}
}
