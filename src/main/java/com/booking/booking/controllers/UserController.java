package com.booking.booking.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.booking.Payload.AddingRoleRequest;
import com.booking.booking.Payload.JwtResponse;
import com.booking.booking.Payload.LoginRequest;
import com.booking.booking.Payload.SignupRequest;
import com.booking.booking.entities.Role;
import com.booking.booking.entities.User;
import com.booking.booking.jwt.JwtUtils;
import com.booking.booking.services.RoleService;
import com.booking.booking.services.UserDetailsImpl;
import com.booking.booking.services.UserService;

@RestController
@RequestMapping("api/public/")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    PasswordEncoder encoder;
  
    @Autowired
    JwtUtils jwtUtils;
    
    @PostMapping("signup")
    public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest){
        if(userService.existsByUserName(signupRequest.getUsername())){
            return ResponseEntity
          .badRequest()
          .body("Error: Username is already taken!");
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body("Error: Email is already in use!");
          }
          User user = new User(signupRequest.getUsername(), 
          signupRequest.getEmail(),
          encoder.encode(signupRequest.getPassword()));
          System.out.println(user.getUsername()+" "+user.getEmail()+user.getPassword()+" user ");
          Set<String> strRoles = signupRequest.getRole();
          Set<Role> roles = new HashSet<>();

          if (strRoles == null){
            // Role userRole=roleService.getRoleByName(strRoles)
            Role defaultRole = roleService.getRoleByName("USER");
            roles.add(defaultRole);
          }
          else{
            // strRoles.forEach(role->{
            //     switch(role){
            //         case "ADMIN":
            //         Role defaultRole = roleService.getRoleByName("USER");
            //         roles.add(defaultRole);
            //         break;
            //         default:
            //         roles.add(new Role("USER"));
            //     }
            // });
            strRoles.forEach(roleName -> {
                Role role = roleService.getRoleByName(roleName);
                roles.add(role);
            });
        }
        // System.out.println(user.getRoles()+"roles before");
          user.setRoles(roles);
        //   System.out.println(user.getRoles()+"roles after");
          User savedUser=userService.createUser(user);
          return ResponseEntity.ok(savedUser);
        //previous code
        // User savedUser= userService.createUser(user);
        // return new ResponseEntity(savedUser,HttpStatus.OK);
    }

    @PostMapping("signin")
    public ResponseEntity<?>  authenticateUser( @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
            List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt, 
                                 userDetails.getId(), 
                                 userDetails.getUsername(), 
                                 userDetails.getEmail(), 
                                 roles));
    }


    @GetMapping
    public ResponseEntity<?> testingRequest(){
      return ResponseEntity.ok("HEllo working fine");
    }
}
