package com.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtAuthFilter) throws Exception {
		System.out.println("🔐 SecurityConfig Loaded - /auth/register is permitted!");
		return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/register").permitAll();
                    auth.requestMatchers("/auth/login").permitAll();
                    
                    auth.requestMatchers(HttpMethod.GET,"/product/{id}").authenticated();
                    auth.requestMatchers(HttpMethod.POST,"/product/saveProduct").authenticated();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    	        .authenticationProvider(authenticationProvider())
    	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				/*
				 * .csrf(csrf -> { csrf.ignoringRequestMatchers("/auth/register",
				 * "/auth/login"); })
				 */
    	        .csrf(csrf -> csrf.disable()) 
                //.oauth2Login(withDefaults())
                //.formLogin(withDefaults())
                .build();
    }
	
	
	 @Bean
	    public AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailsService());
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public UserDetailsService userDetailsService() {
	        return new CustomUserDetailsService();
	    }

}
