package com.user.management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.user.management.model.Role;
import com.user.management.repository.RolesRepository;

import exceptions.ErrorResponse;

@Controller
@RequestMapping(value = "/roleService")
public class RoleController {

    @Autowired
	RolesRepository roleRepo;

    @CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/roles")
    public ResponseEntity<Object> getRoles() { 
        try {
			List<Role> groups = new ArrayList<Role>();
			roleRepo.findAll().forEach(groups::add); 
			return ResponseEntity.ok(groups);

		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new ErrorResponse(99L, "An Error has Occurred, Please try the request again"));
		}
    }

    @CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "/addRole")
	public ResponseEntity<Object> addGroup(@RequestBody(required = true) Role role) {
		try {

			List<Role> existingGroups = roleRepo.findByRoleName(role.getRoleName());
			
			if (existingGroups.size() > 0) {
				return ResponseEntity.badRequest().body(new ErrorResponse(99L, "Group Already Exists!!!"));
			}
			Role roleResponse = roleRepo.save(role);
			return ResponseEntity.accepted().body(roleResponse);

		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

	}
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/updateRole")
	public ResponseEntity<Object> updateUser(@RequestBody Role role) {

		try {
			List<Role> roles = roleRepo.findByRoleName(role.getRoleName());
			if (roles.size() > 0) {
				for (Role r : roles) {
					Optional<Role> updated = roleRepo.findById(r.getRoleId());
					updated.get().setRoleName(role.getRoleName()); 
					updated.get().setRoleDescription(role.getRoleDescription());
					roleRepo.save(updated.get());
				}
				return ResponseEntity.ok(roleRepo.findByRoleName(role.getRoleName())) ;

			} else {
				return ResponseEntity.badRequest().body(new ErrorResponse(99L,"No Group found for Updating data"));

			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

	}
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping(value = "/deleteRole/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable(name = "id") Long roleId) {
		Optional<Role> role = roleRepo.findById(roleId);
		if (role.isEmpty()) {
			return ResponseEntity.notFound().build();

		} else {
			roleRepo.deleteById(roleId);
			return ResponseEntity.ok(new ErrorResponse(0L, "Group Sccessfully Deleted"));
		}
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/roles/{id}")
	public ResponseEntity<Object> getRole(@PathVariable(name="id") Long roleId) { 
		Optional<Role> role = roleRepo.findById(roleId);
		if(role.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(role); 
		}
	}
    
}
