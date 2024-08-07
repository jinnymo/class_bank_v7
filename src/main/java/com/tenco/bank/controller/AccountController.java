package com.tenco.bank.controller;

import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SaveDTO;
import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.repository.model.User;
import com.tenco.bank.service.AccountService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class AccountController {

	private final HttpSession session;
	private AccountService accountService;
	
	@Autowired
	public AccountController(HttpSession session, AccountService accountService) {
		this.session = session;
		this.accountService = accountService;
	}

	/**
	 * - request view create new account - 
	 * url : http://localhost:8080/account/save
	 * @return
	 */
	@GetMapping("/save")
	public String savePage() {

		// verify check needed(all account method)
		User principal = (User) session.getAttribute("principal");
		if (principal == null) {
			throw new UnAuthorizedException("none Authorized User", HttpStatus.UNAUTHORIZED);
		}
		return "account/save";
	}

	/**
	 * method : POST
	 * url : http://localhost:8080/account/save
	 * @param dto
	 * @return
	 */
	@PostMapping("/save")
	public String saveProc(SaveDTO dto) {
		User principal = (User)session.getAttribute("principal");
		
		if (principal == null) {
			throw new UnAuthorizedException("none Authorized User", HttpStatus.UNAUTHORIZED);
		}
		if (dto.getNumber() == null || dto.getNumber().isEmpty()) {
			throw new DataDeliveryException("계좌번호를 입력 하세요", HttpStatus.BAD_REQUEST);
		}
		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException("비밀번호를 입력 하세요", HttpStatus.BAD_REQUEST);
		}
		if (dto.getBalance() == null || dto.getBalance() < 1) {
			throw new DataDeliveryException("초기 입금 금액을 올바르게 입력하세요", HttpStatus.BAD_REQUEST);
		}
		
		accountService.createAccount(dto,principal.getId());
		
		return "redirect:/account/list";
	}
	
}
