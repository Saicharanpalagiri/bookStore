package com.booking.booking.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.booking.booking.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails{

    private Long id;

  private String username;

  private String email;

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String username, String email, String password,
  Collection<? extends GrantedAuthority> authorities) {
this.id = id;
this.username = username;
this.email = email;
this.password = password;
this.authorities = authorities;
}
public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getId(), 
        user.getUsername(), 
        user.getEmail(),
        user.getPassword(), 
        authorities);
  }
public Long getId() {
    return id;
}
public void setId(Long id) {
    this.id = id;
}
public String getUsername() {
    return username;
}
public void setUsername(String username) {
    this.username = username;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}
public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
}
public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
}
@Override
public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
   return true;
}
@Override
public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return true;
  }
@Override
public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
return true;  
}
@Override
public boolean isEnabled() {
    // TODO Auto-generated method stub
return true;   
}
@Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
