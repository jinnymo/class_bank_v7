package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignInDTO;
import com.tenco.bank.dto.SignUpDTO;
import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.repository.model.User;
import com.tenco.bank.service.UserService;
import com.tenco.bank.utils.Define;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;



@Controller
@RequestMapping("/user") // 대문처리
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	private UserService userService;
	private final HttpSession session;
	
	
	/**
	 * 회원 가입 페이지 요청 
	 * 주소 설계 : http://localhost:8080/user/sign-up
	 * @return signUp.jsp
	 */
	@GetMapping("/sign-up")
	public String signUpPage() {
		
		return "user/signUp";
	}
	
	/**
	 * 회원가입 로직 처리 요청
	 * 주소설계 : http://localhost:8080/user/sign-up
	 * @param dto
	 * @return
	 */
	@PostMapping("/sign-up")
	public String signUpProc(SignUpDTO dto) {
		System.out.println("test : "+dto.toString());
		//controller 에서 일반적인 코드 작업
		//1. 인증검사 (여기서는 인증검사 불 필요)
		//2. 유효성 검사
		
		if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_USERNAME, HttpStatus.BAD_REQUEST);
		}
		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
		}
		if (dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_FULLNAME, HttpStatus.BAD_REQUEST);
		}
		
		//서비스 객체로 전달
		userService.creatUser(dto);
		
		
		//TODO - 추후 수정 
		return "redirect:/user/sign-in";
	}
	
	
	/*
	 * 로그인 화면 요청
	 * 주소 설계 : http://localhost:8080/user/sign-in
	 */
	@GetMapping("/sign-in")
	public String signInPage() {
		return "user/signIn";
	}
	
	
	/**
	 * 회원가입 요청 처리
	 * @return
	 */
	@PostMapping("/sign-in")
	public String signProc(SignInDTO dto) {
		
		if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_USERNAME, HttpStatus.BAD_REQUEST);
		}
		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
		}
		User principal = userService.readUser(dto);
		session.setAttribute(Define.PRINCIPAL, principal);
		
		//TODO- 계좌 목록 페이지 이동 처리 예정 || 현재 -> 메인페이지 
		return "redirect:/account/list";
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/user/sign-in";
	}
	
	
	
}











