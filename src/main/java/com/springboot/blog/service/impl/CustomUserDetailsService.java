package com.springboot.blog.service.impl;

import static java.lang.String.format;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException(format("User not found with username or email '%s'", usernameOrEmail)));

        //creation of Granted Authorities
        Set<GrantedAuthority> authoritySet = convertRolesToGrantedAuthorities(user.getRoles());
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), authoritySet);
    }

    private Set<GrantedAuthority> convertRolesToGrantedAuthorities(Set<Role> roles) {
        return roles
                .stream()
                .map(this::convertRoleToGrantedAuthority)
                .collect(Collectors.toSet());
    }

    private GrantedAuthority convertRoleToGrantedAuthority(Role role) {
        return new SimpleGrantedAuthority(role.getName());
    }
}
