package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SignInDTO;
import com.tenco.bank.dto.SignUpDTO;
import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.handler.exception.RedirectException;
import com.tenco.bank.repository.interfaces.UserRepository;
import com.tenco.bank.repository.model.User;

@Service //Ioc (inversion of controll)
public class UserService {
	
	
	//DI - 의존 주입
	@Autowired
	private UserRepository userRepository;
	
	
	/**
	 * 회원 등록 서비스 기능
	 * 트랜잭션 처리
	 * @param dto
	 */
	@Transactional //트랜잭션 처리는 반드시 습관화 
	public void creatUser(SignUpDTO dto) {
		int result = 0;
		
		try {
			result = userRepository.insert(dto.toUser());			
		} catch (DataAccessException e) {
			throw new DataDeliveryException("중복 이름을 사용할 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException("알 수 없는 오류.", HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		if (result != 1) {
			throw new DataDeliveryException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Transactional
	public User readUser(SignInDTO dto) {
		
		User userEntity = null;
		try {
			
			userEntity = userRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
			
		} catch (DataAccessException e) {
			throw new DataDeliveryException("잘못된 처리입니다.",HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException("알수 없는 오류", HttpStatus.SERVICE_UNAVAILABLE);
		}
		if (userEntity == null) {
			throw new DataDeliveryException("아이디 또는 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
		}
		
		return userEntity;
	}
	
}















