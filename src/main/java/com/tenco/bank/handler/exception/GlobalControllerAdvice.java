package com.tenco.bank.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseEntity<Object> handleResourceNotFoundException(Exception e){
		System.out.println("GlobalControllerAdvice 오류확인 : ");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		
	}
}
