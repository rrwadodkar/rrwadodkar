package com.user.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.user.management.model.User;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

	public List<User> findByUserName(String userName);

}
