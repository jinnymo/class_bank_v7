package com.tenco.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller //IOC 대상(싱글톤 패턴 관리가 된다.)  -- 제어의 역전 개념
public class MainController {

	//REST API 기반으로 주소를 설계 가능
	
	
	//주소 설계 
	//http://localhost:8080/main-page
	@GetMapping("/main-page")
	public String mainPage() {
		System.out.println("mainPage() 호출 확인");
		//[Jsp 파일 찾기(yml)] - 뷰 리졸
		//prefix: /WEB-INF/view
		//        /main
		//suffix: .jsp
		
		return "/main";
	}
	
}










