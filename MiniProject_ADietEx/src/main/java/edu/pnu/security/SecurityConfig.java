package edu.pnu.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import ch.qos.logback.core.filter.Filter;
import edu.pnu.config.JWTAuthenticationFilter;
import edu.pnu.config.JWTAuthorizationFilter;
import edu.pnu.persistence.MemberRepository;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;

	@Autowired
	private MemberRepository memRepo;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth->auth
				.requestMatchers(new AntPathRequestMatcher("/main/**")).authenticated()
				.anyRequest().permitAll()
				);
		
		http.csrf(cs->cs.disable());
		
		http.formLogin(frmLogin->frmLogin.disable());
		http.httpBasic(basic->basic.disable());
		
		http.sessionManagement(ssmn->ssmn.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		// 로그인 인증 및 토큰 등록
		http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));
		
		http.addFilterBefore(new JWTAuthorizationFilter(memRepo), AuthorizationFilter.class);
		
		

		return http.build();
	}
	
}
