package com.learn.service;


import java.security.Principal;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.model.Users;
import com.learn.repostiory.UserRepo;

@Service
public class UserService {
	
	
	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;



	@Autowired
	public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;

	}

	public List<Users> getAllUsers(){
		return userRepo.findAll();
		
	}
	
	public Users getUser(String username) {
//		return this.list.stream().filter((users) -> users.getUserName().equals(username) );
		return userRepo.findByUserName(username);
	}
	
	public Users addUser(Users user) {
		Users user1=new Users();
		user1.setEmail(user.getEmail());
		user1.setUserName(user.getUserName());
//		user1.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user1.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user1.setRole(user.getRole());
		
		return userRepo.save(user1);
	}
	

	
	public Users updateUser(Users user,Principal principal) {
		String username=principal.getName();
		System.out.println("UNS: "+username);

		Users existingUser=userRepo.findByUserName(username);
				if(existingUser == null) {
					throw new RuntimeException("User Name not found");
				}
				
				existingUser.setEmail(user.getEmail());
				existingUser.setUserName(user.getUserName());
//				existingUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
				existingUser.setPassword((this.passwordEncoder.encode(user.getPassword())));
				existingUser.setRole(user.getRole());
                userRepo.save(existingUser);
	
		return existingUser;
	}
	
	@Transactional
	public void deleteUserIfPasswordMatches(String username,String rawPassword) {
		Users user=userRepo.findByUserName(username);
//		System.out.println(rawPassword +"and"+user.getPassword());
		if(user == null){
			throw new RuntimeException("User not found");
			
		}
		if(!passwordEncoder.matches(rawPassword,user.getPassword())) {
             throw new RuntimeException("Invalid Password");	
        }
      
		userRepo.delete(user);
}
	}
