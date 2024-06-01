package com.in28minutes.rest.webservices.restfulwebservices.role.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.in28minutes.rest.webservices.restfulwebservices.jwt.JwtTokenService;
import com.in28minutes.rest.webservices.restfulwebservices.role.ApplicationUser;
import com.in28minutes.rest.webservices.restfulwebservices.role.LoginResponseDTO;
import com.in28minutes.rest.webservices.restfulwebservices.role.Role;
import com.in28minutes.rest.webservices.restfulwebservices.role.repository.RoleRepository;
import com.in28minutes.rest.webservices.restfulwebservices.role.repository.UserRepository;

@Transactional
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    public ApplicationUser registerUser(String username, String password) {

        String encodedPassword = passwordEncoder.encode(password);

        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return userRepository.save(new ApplicationUser(0, username, encodedPassword, authorities));
    }

    public LoginResponseDTO loginUser(String username, String password) {
        try {

            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = jwtTokenService.generateToken(auth);
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
        }
    }
}
