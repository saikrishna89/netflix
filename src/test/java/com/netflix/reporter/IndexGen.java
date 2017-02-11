package com.netflix.reporter;

public class IndexGen {

	int slNo;
	String testId;
	String testCase;
	String testDesc;
	int steps;
	int pass;
	int fail;
	int incomplete;
	String status;
	String duration;
	String History;

	public int getSlNo() {
		return slNo;
	}

	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}
	public String getTestCase() {
		return testCase;
	}

	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}

	public String getTestDesc() {
		return testDesc;
	}
	
	public void  setHistory(String History ) {
		this.History= History;		
	}
	
	public String getHistory() {
		return History;		
	}

	public void setTestDesc(String testDesc) {
		this.testDesc = testDesc;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public int getIncomplete() {
		return incomplete;
	}

	public void setIncomplete(int incomplete) {
		this.incomplete = incomplete;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String strDiff) {
		this.duration = strDiff;
	}

}
