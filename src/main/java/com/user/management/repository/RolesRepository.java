package com.user.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.user.management.model.Role;
import java.util.List;


@Component
public interface RolesRepository  extends JpaRepository<Role,Long>{

    public List<Role> findByRoleName(String roleName);
    
}
