package com.rapl.carros.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 1. quando for fazer a injeção de dependência, utilize o objeto definido 
	//    em @Service(value = "userDetailsService") UserDatailsServiceImpl
	// 2. Após a criação dessa classe de serviço UserDatailsServiceImpl.java, altera-se o auth abaixo incluindo o 
	//    auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/welcome", "/").permitAll()
			.anyRequest().authenticated()
			.and()
		.httpBasic()
			.and()
		.csrf().disable();
	}
	
	// o scrip abaixo cria uma autenticação em memória com dois usuários.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);

//        auth
//            .inMemoryAuthentication().passwordEncoder(encoder)
//                .withUser("user").password(encoder.encode("user")).roles("USER")
//                .and()
//                .withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN");
	}
}

// EXEMPLO: 
//6.4 Authorize Requests
//Our examples have only required users to be authenticated and have done so for every URL in our application. We can specify custom requirements for our URLs by adding multiple children to our http.authorizeRequests() method. For example:
//
//protected void configure(HttpSecurity http) throws Exception {
//    http
//        .authorizeRequests()                                                                1
//            .antMatchers("/resources/**", "/signup", "/about").permitAll()                  2
//            .antMatchers("/admin/**").hasRole("ADMIN")                                      3
//            .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")            4
//            .anyRequest().authenticated()                                                   5
//            .and()
//        // ...
//        .formLogin();
//}
