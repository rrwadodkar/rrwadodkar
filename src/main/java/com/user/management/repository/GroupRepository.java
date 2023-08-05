package com.user.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.user.management.model.Group;

@Component
public interface GroupRepository extends JpaRepository<Group, Long> {

	public List<Group> findByGroupName(String groupName);

}