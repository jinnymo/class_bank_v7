package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.repository.model.History;


@Mapper
public interface HistoryRepository {
	
	public int insert(History histroy);
	public int updateById(History histroy);
	public int deleteById(Integer id);
	
	public History findById(Integer id);
	public List<History> findAll();
	
	// 코드 추가예정 - 모델을 반드시 1:1 매핑 시킬 필요는 없다.
	// 조인 쿼리 서브쿼리 
}