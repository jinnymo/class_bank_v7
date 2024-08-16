package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.tenco.bank.dto.KakaoProfile;
import com.tenco.bank.dto.OAuthToken;
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
	
	@Value("${tenco.key}")
	private String tencoKey;
	/**
	 * 회원 가입 페이지 요청 
	 * 주소 설계 : http://localhost:8080/user/sign-up
	 * @return signUp.jsp
	 */
	@GetMapping("/sign-up")
	public String signUpPage() {
		
		return "user/signUp";
	}
	
	
	@GetMapping("/kakao")
	public String kakaoLogin(@RequestParam(name ="code") String code) {
		
		RestTemplate rt1 = new RestTemplate();
		HttpHeaders header1 = new HttpHeaders();
		header1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, String> params1 = new LinkedMultiValueMap<>();
		params1.add("grant_type", "authorization_code");
		params1.add("client_id", "c9133bb1945e2fd143b7ef2c8af37a6d");
		params1.add("redirect_uri", "http://localhost:8080/user/kakao");
		params1.add("code", code);
		
		HttpEntity<MultiValueMap<String, String>> reqkakaoToken = new HttpEntity<>(params1,header1);
		ResponseEntity<OAuthToken> response1 
				= rt1.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,reqkakaoToken,OAuthToken.class);
		
	
		//System.out.println(response1.getBody().getAccessToken());
		
		
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders header2 = new HttpHeaders();
		header2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		header2.add("Authorization", "Bearer "+ response1.getBody().getAccessToken());
		
		HttpEntity<MultiValueMap<String, String>> reqkakaoToken2 = new HttpEntity<>(header2);
		ResponseEntity<KakaoProfile> response2 
				= rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET,reqkakaoToken2,KakaoProfile.class);
		
		KakaoProfile kakaoProfile = response2.getBody();
		
		//회원가입용 데이타 
		SignUpDTO signUpDTO = SignUpDTO.builder()
									.username(kakaoProfile.getProperties().getNickname()+"_"+kakaoProfile.getId())
									.fullname("OAuth_"+kakaoProfile.getProperties().getNickname())
									.password(tencoKey)
									.build();
		
		//우리 사이트 최소 소셜 사용자 인지 판별
		User oldUser = userService.searchUsername(signUpDTO.getUsername());
		if (oldUser == null) {
			//사용자가 최소 로그인
			oldUser = new User();
			userService.creatUser(signUpDTO);
			
			oldUser.setUsername(signUpDTO.getUsername());
			oldUser.setPassword(null);
			oldUser.setFullname(signUpDTO.getFullname());
		}
		
		
		session.setAttribute(Define.PRINCIPAL, oldUser);
		return "redirect:/account/list";
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











