package com.booking.booking.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.booking.booking.entities.Role;
import com.booking.booking.entities.User;
import com.booking.booking.repositories.RoleRepository;
import com.booking.booking.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User createUser(User user){
        // User user=new User(email,password,firstName,lastName);
        User savedUser=userRepository.save(user);
        return savedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }
    
    public Boolean existsByUserName(String username){
        Optional<User> user=userRepository.findByUsername(username);
        if(!user.isEmpty()){
            return true;
        }
        return false;
    }

    public Boolean existsByEmail(String email){
        Optional<User> user=userRepository.findByEmail(email);
        if(!user.isEmpty()){
            return true;
        }
        return false;
    }

    public User getRoles(String userName,String userNameOfRoleTobeAssigned){
        User user=userRepository.findByUsername(userName).get();
        Set<Role> roles=user.getRoles();
        User userToBeRoleAssigned=userRepository.findByUsername(userNameOfRoleTobeAssigned).get();
        Boolean role=roles.stream().anyMatch(r->r.getName().equals("ADMIN"));
        Set<Role> rolesOfExistingUser=userToBeRoleAssigned.getRoles();
        Boolean roleOfExistingUser=rolesOfExistingUser.stream().anyMatch(r->r.getName().equals("ADMIN"));
        if(role && !roleOfExistingUser){
            Role r=roleRepository.findByName("ADMIN").get();
            rolesOfExistingUser.add(r);
        }else{
            System.out.println("role already assigned");
            return userToBeRoleAssigned;
        }
        userToBeRoleAssigned.setRoles(rolesOfExistingUser);
        userRepository.save(userToBeRoleAssigned);
        return userToBeRoleAssigned;
    }
}
