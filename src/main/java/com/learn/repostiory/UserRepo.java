package com.learn.repostiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

	Users findByUserName(String userName);



}
