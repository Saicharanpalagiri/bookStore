package com.booking.booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import com.booking.booking.entities.Role;
import com.booking.booking.repositories.RoleRepository;
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role getRoleByName(String roleName){
        return roleRepository.findByName(roleName).get();
    }
}
