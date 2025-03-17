package com.ecommerce.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.security.pojo.UserEntity;
import com.ecommerce.security.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
       
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
       
       UserEntity userDetails = user.get();
       System.out.println("🔍 Found User: " + userDetails.getUsername());
       System.out.println("👤 User Role: " + userDetails.getRole());
       System.out.println("🔑 Encrypted Password: " + userDetails.getPassword());
       
        return new UserPrincipal(user.get());
    }
}

