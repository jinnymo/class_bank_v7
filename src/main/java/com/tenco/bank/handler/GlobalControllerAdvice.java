package com.tenco.bank.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.handler.exception.RedirectException;

@ControllerAdvice	//HTML 렌더링 예외에 많이 사용
public class GlobalControllerAdvice {

	/*
	 * (개발시에 많이 활용)
	 * 모든 예외 클래스를 알 수 없기 때문에 로깅으로 확인 할 수 있도록 설정
	 * 로깅처리 - 동기적 방식(system.out.println), @slf4j(비동기 처리 됨)
	 */
	
	@ExceptionHandler(Exception.class)
	public void exception(Exception e) {
		System.out.println("----------------------------------");
		System.out.println(e.getClass().getName());
		System.out.println(e.getMessage());
		System.out.println("----------------------------------");	
	}
	
	//예외를 내릴 때 데이터를 내리고 싶다면 1. @RestControllerAdvice 를 사용하면 된다.
	//단 @ControllerAdvice 를 사용하고 있다면 @ResponseBody 를 사용하면 되낟.
	@ResponseBody
	@ExceptionHandler(DataDeliveryException.class)	
	public String dataDeleveryException(DataDeliveryException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");

		sb.append("alert(`"+ e.getMessage() +"`);");		
		sb.append("history.back()"); //뒤로가기 기능
		
		sb.append("</script>");
		
		return sb.toString();
	}
	
	@ResponseBody
	@ExceptionHandler(com.tenco.bank.handler.exception.UnAuthorizedException.class)
	public String UnAuthorizedException(com.tenco.bank.handler.exception.UnAuthorizedException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");

		sb.append("alert(`"+ e.getMessage() +"`);");		
		sb.append("location.href='/user/sign-in';"); //뒤로가기 기능
		
		sb.append("</script>");
		
		return sb.toString();
	}
	
	/*
	 * 에러페이지로 이동 처리
	 * jsp로 이동시 데이터를 담아서 보내는 방법
	 * ModelAndView, Model 사용가능
	 * throw new RedirectExeception("페이지 없음",404)
	 */
	@ExceptionHandler(RedirectException.class)
	public ModelAndView redirectException(RedirectException e) {
		ModelAndView modelAndView = new ModelAndView("errorPage");
		modelAndView.addObject("statusCode",e.getStatus().value());
		modelAndView.addObject("message",e.getMessage());
		
		return modelAndView;
	}
	
}
