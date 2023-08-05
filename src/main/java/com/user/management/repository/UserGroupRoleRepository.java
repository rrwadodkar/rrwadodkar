package com.user.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.management.model.UserGroupMapping;

public interface UserGroupRoleRepository  extends JpaRepository<UserGroupMapping,Long>{
    
}
