package com.booking.booking.entities;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import java.util.*;
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String username;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles=new HashSet(); 


    public User(String username,String email, String password) {
        this.email = email;
        this.password =password;
        this.username=username;
    }
    public User(){}
}
