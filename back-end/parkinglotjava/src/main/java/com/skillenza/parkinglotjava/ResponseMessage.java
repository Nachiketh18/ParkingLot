package com.skillenza.parkinglotjava;

import org.springframework.http.HttpStatus;

public class ResponseMessage {
	
	private String message;
	private HttpStatus code;
	
	ResponseMessage(){}
	
	ResponseMessage(String message,HttpStatus code){
		this.message=message;
		this.code=code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getCode() {
		return code;
	}

	public void setCode(HttpStatus code) {
		this.code = code;
	}
	
	

}
