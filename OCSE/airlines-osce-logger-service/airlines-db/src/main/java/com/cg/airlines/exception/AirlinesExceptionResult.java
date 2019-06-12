package com.cg.airlines.exception;

public class AirlinesExceptionResult
{
	private int errorCode;
	private String errorStatus;
	public AirlinesExceptionResult() {
		super();
	}


	public AirlinesExceptionResult(int errorCode, String errorStatus) {
		super();
		this.errorCode = errorCode;
		this.errorStatus = errorStatus;
	}


	public int getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}


	public String getErrorMessage() {
		return errorStatus;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorStatus = errorMessage;
	}

}
