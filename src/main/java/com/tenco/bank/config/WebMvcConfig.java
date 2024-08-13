package com.tenco.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenco.bank.handler.AuthInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor //아래 생성자 주석 부분 대신 사용 가능
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired //DI(dependency injection)
	private final AuthInterceptor authInterceptor;
	
	
//	public WebMvcConfig(AuthInterceptor authInterceptor) {
//		this.authInterceptor = authInterceptor;
//	}
	
	
	//우리가 만들어 놓은 AuthInterceptors를 등록 해야한다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(authInterceptor)
			.addPathPatterns("/account/**")
			.addPathPatterns("/auth/**");
	}

	@Bean //IOC대상 (싱글톤 처리)
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}












