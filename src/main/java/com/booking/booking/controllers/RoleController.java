package com.booking.booking.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.booking.booking.Payload.AddingRoleRequest;
import com.booking.booking.entities.Role;
import com.booking.booking.entities.User;
import com.booking.booking.repositories.RoleRepository;
import com.booking.booking.services.RoleService;
import com.booking.booking.services.UserService;

@RestController
@RequestMapping("api/private/role/")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        Role savedRole = roleService.createRole(role);
        return new ResponseEntity(savedRole,HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> listRoles(){
        System.out.println("entered");
        List<Role> roles=roleService.findAll();
        return new ResponseEntity(roles,HttpStatus.OK);
     }

     @PostMapping("addrole")
    public ResponseEntity<?> addRole(@RequestBody AddingRoleRequest addingRoleRequest){
      System.out.println(addingRoleRequest.getUsername()+" un ");
      User user=userService.getRoles(addingRoleRequest.getUsername(),addingRoleRequest.getUserNameOfRoleTobeAssigned());
      return new ResponseEntity(user,HttpStatus.OK);
    }
}
