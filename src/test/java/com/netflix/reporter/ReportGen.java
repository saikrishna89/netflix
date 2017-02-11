package com.netflix.reporter;

import java.sql.Timestamp;

public class ReportGen {
	String step;
	String stepDescription;
	String expectedResult;
	String actualResult;
	String status;
	String screenShotFileName;
//	String stepTimeStamp;
	Timestamp stepTimeStamp;

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getStepDescription() {
		return stepDescription;
	}

	public void setStepDescription(String stepDescription) {
		this.stepDescription = stepDescription;
	}

	public String getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getActualResult() {
		return actualResult;
	}

	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScreenShotFileName() {
		return screenShotFileName;
	}

	public void setScreenShotFileName(String snapShot) {
		this.screenShotFileName = snapShot;
	}
	
	public Timestamp getTimeStamp() {
		return stepTimeStamp;
	}
		
	public void setTimeStamp() {
		this.stepTimeStamp = new Timestamp(System.currentTimeMillis());
	}
}
