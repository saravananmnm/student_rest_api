package com.student.service;

import com.student.dto.User_DTO;
import com.student.entity.Role;
import com.student.entity.User;
import com.student.exception.ResourceNotFoundException;
import com.student.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = getByUsername(username);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_"+user.getRole().name())).disabled(!user.isEnabled()).build();

    }


    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User_DTO create(User_DTO user){
        User user1 = new User();
        user1.setId(user.getId());
        user1.setUsername(user.getUsername());
        user1.setPassword(passwordEncoder.encode(
                user.getPassword()
        ));
        user1.setRole(Role.valueOf(user.getRole().trim().toUpperCase()));
        user1.setEmail(user.getEmail());
        user1.setEnabled(user.isEnabled());
        User saved = userRepository.save(user1);
        return toDTO(saved);
    }

    public List<User_DTO> findAll(){
        List<User> users = userRepository.findAll();
        return users.stream().map(this::toDTO).toList();
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));
    }

    public User_DTO update(User_DTO user){
        User user1 = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user1.setUsername(user.getUsername());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setEmail(user.getEmail());
        user1.setEnabled(user.isEnabled());
        User saved = userRepository.save(user1);
        return toDTO(saved);
    }



    public User_DTO toDTO(User user){
        return  new User_DTO(user.getId(),user.getUsername(),user.getPassword(),user.getRole().name(),user.getEmail(),user.isEnabled());
    }
}
