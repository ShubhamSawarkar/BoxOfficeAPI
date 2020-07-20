package com.restapi.config;

import com.restapi.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailServiceImpl userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncrypter()
	{
		return new BCryptPasswordEncoder(); 
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncrypter());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable();
		
		http.authorizeRequests().antMatchers("/hello").permitAll();
		
		http.authorizeRequests().antMatchers("/reserve").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
		
		http.authorizeRequests().antMatchers("/addSeat", "/addShow").access("hasRole('ROLE_ADMIN')");
		
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/accessDenied");
		
		http.authorizeRequests().and().formLogin()
		
		.loginProcessingUrl("/login")
		.defaultSuccessUrl("/loginSuccess")
		.failureUrl("/loginFailure")
		.usernameParameter("Username")
		.passwordParameter("Password")
		.and().logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/logoutSuccess");
	}
}


