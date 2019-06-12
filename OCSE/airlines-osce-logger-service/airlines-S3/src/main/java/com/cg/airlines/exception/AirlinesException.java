package com.cg.airlines.exception;

public class AirlinesException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private int code;
	private String status;
	public AirlinesException() {

	}

	public AirlinesException(String String) {
		super(String);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AirlinesException(int code, String status) {
		super();
		this.code = code;
		this.status = status;
	}

	
}
