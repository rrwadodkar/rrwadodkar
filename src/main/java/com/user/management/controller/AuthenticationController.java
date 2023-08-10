package com.user.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.management.services.UserDetailsService;
import com.user.management.utils.TokenUtil;

@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authManager; 
	
	@Autowired
	private TokenUtil tokenUtil; 
	
	@Autowired
	private UserDetailsService userDetailsService; 
	
	@RequestMapping(value = "/authenticate")
	public ResponseEntity<Object> authenticate(@RequestHeader HttpHeaders req ) {
		
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(, authManager))
		} catch(Exception e) {
			
		}
		return null; 
		
	}
	
}
