package com.web.liner.util;

public class LineException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9210407801630943137L;
	private int code;
	private String msg;
	
	
	public LineException(Throwable cause) {
		super(cause);
		this.code = 99991;
		this.msg = "system error";
	}
	
	public LineException(String msg, Throwable cause) {
		super(msg, cause);
		this.code = 99991;
		this.msg = "system error";
	}
	
	public LineException() {
		super();
		this.code = 99991;
		this.msg = "system error";
	}
	
	public LineException(int code) {
		super();
		this.code = code;
		this.msg = "system error";
	}
	
	public LineException(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getMsg() {
		return this.msg;
	}

}
