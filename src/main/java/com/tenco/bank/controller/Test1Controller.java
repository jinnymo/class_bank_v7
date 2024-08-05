package com.tenco.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenco.bank.handler.UnAuthorizedException;
import com.tenco.bank.repository.model.User;

// Controller -> (뷰리졸버 동작 --> JSP 파일 찾아서 렌더링 처리 한다.)
//RestController -> 뎅터를 반환 처
@RestController //Controller + Rest API || @Controller + @ResponseBody
public class Test1Controller {
	
	
	@GetMapping("/test1")
	public User test1() {
		// Gson ->json 형식으로 변환 --> String 응답처리
		try {
			int result = 10/0;			
		} catch (Exception e) {
			throw new UnAuthorizedException("인증이 안된 사용자 입니다.",HttpStatus.FORBIDDEN);
		}
		User user = User.builder().username("jinny").password("asd123").build();
		return user;
	}
	
}
