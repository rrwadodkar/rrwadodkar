package com.user.management.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.management.model.User;
import com.user.management.repository.UserRepository;

@Service
public class DBUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> users= userRepo.findByUserName(username);
		if(users.isEmpty()) throw new UsernameNotFoundException("User not found");
		else return new org.springframework.security.core.userdetails.User(users.get(0).getUserName(), users.get(0).getPassword(),new ArrayList<>());
	}

}
