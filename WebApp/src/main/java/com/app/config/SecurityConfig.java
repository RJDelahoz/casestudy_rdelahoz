package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("com.app")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final AccessDeniedHandler accessDeniedHandler;
	private final CustomSuccessHandler customSuccessHandler;

	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService, AccessDeniedHandler accessDeniedHandler,
						  CustomSuccessHandler customSuccessHandler) {
		this.userDetailsService = userDetailsService;
		this.accessDeniedHandler = accessDeniedHandler;
		this.customSuccessHandler = customSuccessHandler;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	public void configure(WebSecurity web) {
		web
				.ignoring()
				.antMatchers("/js/**", "/img/**", "/css/**", "/resources/**", "/scripts/**");
	}

	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/multi-user-select").permitAll()
				.antMatchers("/register-manager").permitAll()
				.antMatchers("/register-user").permitAll()
				.antMatchers("/password-recovery").permitAll()
				.antMatchers("/contactus").permitAll()
				.antMatchers("/welcome").hasAnyAuthority("USER")
				.antMatchers("/tickets", "/tickets-closed").hasAnyAuthority("USER")
				.antMatchers("/ticket-center", "/ticket-center-closed").hasAnyAuthority("MANAGER")
				.antMatchers("/view-ticket").hasAnyAuthority("MANAGER", "USER")
				.antMatchers("/property", "/properties").hasAnyAuthority("MANAGER")
				.antMatchers("/memo").hasAnyAuthority("MANAGER")
				.antMatchers("/joinProperty").hasAnyAuthority("USER")
				.antMatchers("/admin/**").hasAnyAuthority("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login")
				.loginProcessingUrl("/loginAction").usernameParameter("username").passwordParameter("password")
				.successHandler(customSuccessHandler).permitAll()
				.and()
				.logout().logoutSuccessUrl("/").permitAll()
				.and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
				.and()
				.csrf().disable();
	}
}