package com.ecommerce.security.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.security.AuthService;
import com.ecommerce.security.JwtUtils;
import com.ecommerce.security.pojo.UserEntity;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserEntity user) {
		System.out.println("@@@@@@@@@@");
		return ResponseEntity.ok(authService.register(user));
	}

	/*
	 * @PostMapping("/login") public ResponseEntity<String> login(@RequestBody
	 * Map<String, String> loginRequest) { return
	 * ResponseEntity.ok(authService.login(loginRequest.get("username"),
	 * loginRequest.get("password"))); }
	 */

	public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
			UserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody UserEntity loginRequest) {
		System.out.println("@@@@@@@@@@" + loginRequest.getUsername() +""+ loginRequest.getPassword());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateToken(loginRequest.getUsername());
		System.out.println(token);
        return ResponseEntity.ok((token));
	}
}
