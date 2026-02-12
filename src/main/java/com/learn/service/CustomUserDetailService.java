package com.learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learn.model.Users;
import com.learn.repostiory.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	private final UserRepo userRepo ;
	
	@Autowired
	public CustomUserDetailService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=userRepo.findByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException("User with"+username+" not found");
		}
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUserName())
				.password(user.getPassword())
				.roles(user.getRole())
				.build();
	}

}
