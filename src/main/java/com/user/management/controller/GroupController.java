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

import com.user.management.model.Group;
import com.user.management.repository.GroupRepository;
import exceptions.ErrorResponse;

@Controller
@RequestMapping(value = "/groupService")
public class GroupController {

    @Autowired
	GroupRepository groupRepo;

    @CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/groups")
    public ResponseEntity<Object> getGroups() { 
        try {
			List<Group> groups = new ArrayList<Group>();
			groupRepo.findAll().forEach(groups::add); 
			return ResponseEntity.ok(groups);

		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new ErrorResponse(99L, "An Error has Occurred, Please try the request again"));
		}
    }

    @CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "/addGroup")
	public ResponseEntity<Object> addGroup(@RequestBody(required = true) Group group) {
		try {

			List<Group> existingGroups = groupRepo.findByGroupName(group.getGroupName());
			
			if (existingGroups.size() > 0) {
				return ResponseEntity.badRequest().body(new ErrorResponse(99L, "Group Already Exists!!!"));
			}
			Group groupResponse = groupRepo.save(group);
			return ResponseEntity.accepted().body(groupResponse);

		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

	}
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/updateGroup")
	public ResponseEntity<Object> updateUser(@RequestBody Group group) {

		try {
			List<Group> groups = groupRepo.findByGroupName(group.getGroupName());
			if (groups.size() > 0) {
				for (Group g : groups) {
					Optional<Group> updated = groupRepo.findById(g.getId());
					updated.get().setGroupName(group.getGroupName());
					groupRepo.save(updated.get());
				}
				return ResponseEntity.ok(groupRepo.findByGroupName(group.getGroupName())) ;

			} else {
				return ResponseEntity.badRequest().body(new ErrorResponse(99L,"No Group found for Updating data"));

			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

	}
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping(value = "/deleteGroup/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable(name = "id") Long groupId) {
		Optional<Group> group = groupRepo.findById(groupId);
		if (group.isEmpty()) {
			return ResponseEntity.notFound().build();

		} else {
			groupRepo.deleteById(groupId);
			return ResponseEntity.ok(new ErrorResponse(0L, "Group Sccessfully Deleted"));
		}
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/groups/{id}")
	public ResponseEntity<Object> getUser(@PathVariable(name="id") Long userId) { 
		Optional<Group> group = groupRepo.findById(userId);
		if(group.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(group); 
		}
	}
    
}
