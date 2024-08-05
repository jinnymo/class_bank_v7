package com.tenco.bank.repository.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Account {

	private Integer id;
	private String number;
	private String password;
	private Long balance;
	private Integer userId;
	private Timestamp createdAt;
	
	// 출금 기능 메서드
	public void widthdraw(Long amount) {
		// 방어적 코드 
		this.balance -= amount;
	}
	// 입금 기능 메서드 
	public void deposit(Long amount) {
		this.balance += amount;
	}
	// 패스워드 체크
	//잔액 역부 확인 
	//계좌 소유자 확인 기능
}
