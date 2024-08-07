package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SaveDTO;
import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.handler.exception.RedirectException;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.repository.model.Account;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	
	@Transactional
	public void createAccount(SaveDTO dto, int userId) {
		int result = 0;
		
		try {
			result = accountRepository.insert(dto.createAccount(userId));
		}  catch (DataAccessException e) {
			throw new DataDeliveryException("중복 계좌번호를 사용할 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException("알 수 없는 오류.", HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		if (result != 1) {
			throw new DataDeliveryException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
