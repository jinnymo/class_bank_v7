package com.tenco.bank.handler;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UnAuthorizedException extends RuntimeException {

	private HttpStatus status;
	
	//throw new Exception( , ) 
	public UnAuthorizedException(String message, HttpStatus status) {
		super(message);
		this.status = status;
		
	}
}
