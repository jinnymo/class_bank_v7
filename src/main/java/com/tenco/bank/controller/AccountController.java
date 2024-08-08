package com.tenco.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SaveDTO;
import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.model.Account;
import com.tenco.bank.repository.model.User;
import com.tenco.bank.service.AccountService;
import com.tenco.bank.utils.Define;

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
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		if (principal == null) {
			throw new UnAuthorizedException(Define.NOT_AN_AUTHENTICATED_USER, HttpStatus.UNAUTHORIZED);
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
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		if (principal == null) {
			throw new UnAuthorizedException(Define.NOT_AN_AUTHENTICATED_USER, HttpStatus.UNAUTHORIZED);
		}
		if (dto.getNumber() == null || dto.getNumber().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_ACCOUNT_NUMBER, HttpStatus.BAD_REQUEST);
		}
		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
		}
		if (dto.getBalance() == null || dto.getBalance() < 1) {
			throw new DataDeliveryException(Define.ENTER_YOUR_BALANCE, HttpStatus.BAD_REQUEST);
		}
		
		accountService.createAccount(dto,principal.getId());
		
		return "redirect:/account/list";
	}
	
	
	/**
	 * 계좌 목록 화면 요청
	 * url : http://localhost:8080/account/list
	 * @return
	 */
	@GetMapping("/list")
	public String listPage(Model model) {
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if (principal == null) {
			throw new UnAuthorizedException(Define.ENTER_YOUR_LOGIN,HttpStatus.UNAUTHORIZED);
		}
		List<Account> accountList = accountService.readAccountListByUserId(principal.getId());
		if (accountList.isEmpty()) {
			model.addAttribute(Define.ACCOUNT_LIST,null);
		}else {			
			model.addAttribute(Define.ACCOUNT_LIST,accountList);
		}
		return "account/list";
	}
}













