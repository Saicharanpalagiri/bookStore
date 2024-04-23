package com.booking.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.booking.booking.entities.Role;
import com.booking.booking.entities.User;
import com.booking.booking.repositories.RoleRepository;
import com.booking.booking.repositories.UserRepository;
import com.booking.booking.services.UserService;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.createUser(user);

        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUsername());
        assertEquals("test@example.com", savedUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testLoadUserByUsername() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    public void testExistsByUserNameTrue() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(new User()));

        assertTrue(userService.existsByUserName("testUser"));
    }

    @Test
    public void testGetRoles() {
        User adminUser = new User();
        adminUser.setUsername("adminUser");
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        adminUser.setRoles(Set.of(adminRole));

        User normalUser = new User();
        normalUser.setUsername("normalUser");

        when(userRepository.findByUsername("adminUser")).thenReturn(Optional.of(adminUser));
        when(userRepository.findByUsername("normalUser")).thenReturn(Optional.of(normalUser));
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(adminRole));

        User userWithAdminRole = userService.getRoles("adminUser", "normalUser");

        assertNotNull(userWithAdminRole);
        assertTrue(userWithAdminRole.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN")));
        verify(userRepository, times(1)).save(userWithAdminRole);
    }
}
