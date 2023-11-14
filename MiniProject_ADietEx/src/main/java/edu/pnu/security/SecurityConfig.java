package edu.pnu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	PasswordEncoder encode() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth->auth
				.requestMatchers(new AntPathRequestMatcher("/api/main/**")).authenticated()
				.anyRequest().permitAll()
				);
		
		http.csrf(cs->cs.disable());
		
		http.formLogin(form->form
				.loginPage("/login")
				.defaultSuccessUrl("/api/main", true)
				);
		
		http.exceptionHandling(ex->ex.accessDeniedPage("/api/accessDenied"));
		
		http.logout(logout->logout.logoutUrl("/api/logout").invalidateHttpSession(true).logoutSuccessUrl("/"));
		
		// 로그인 인증 및 토큰 등록

		return http.build();
	}
	
}
