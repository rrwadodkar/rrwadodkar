package com.user.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.user.management.services.DBUserDetailsService;
import com.user.management.utils.TokenUtil;

import exceptions.ErrorResponse;

@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authManager; 
	
	@Autowired
	private TokenUtil tokenUtil; 
	
	@Autowired
	private DBUserDetailsService userDetailsService; 
	
	@GetMapping(value = "/authenticate")
	public ResponseEntity<Object> authenticate(@RequestHeader HttpHeaders req ) {
		
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getFirst("username"), req.getFirst("password")));
			UserDetails userDetails = userDetailsService.loadUserByUsername(req.getFirst("username"));
			String token = tokenUtil.generateToken(userDetails);
			return ResponseEntity.accepted().body(token); 

		} catch(Exception e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(99L,"Unable to authenticate user"));
		}
	}
	
}
