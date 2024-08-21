package com.springReactTemp.SpringReactTemp.controllers;

import com.springReactTemp.SpringReactTemp.model.AppUser;
import com.springReactTemp.SpringReactTemp.response.AuthResponse;
import com.springReactTemp.SpringReactTemp.securityConfig.JwtProvider;
import com.springReactTemp.SpringReactTemp.services.AppUserDetails;
import com.springReactTemp.SpringReactTemp.services.AppUserDetailsService;
import com.springReactTemp.SpringReactTemp.services.AppUserServiceImplementation;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.springReactTemp.SpringReactTemp.repository.AppUserRepository;
import com.springReactTemp.SpringReactTemp.services.AppUserService;


import java.util.List;

@RestController
@RequestMapping("/auth")
public class AppUserController {

    @Autowired
    private AppUserRepository AppUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppUserServiceImplementation customAppUserDetails;
    @Autowired
    private AppUserService AppUserService;


    @PostConstruct
    public void initRoleAndUser() {
        AppUser u = new AppUser();
        u.setRole(Role.ROLE_USER);
        u.setEmail("user");
        u.setPassword(passwordEncoder.encode("password"));
        u.setFirstName("user");
        u.setLastName("user");
        AppUserRepository.save(u);
        System.out.println("User added");
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createAppUserHandler(@RequestBody AppUser AppUser) {
        String email = AppUser.getEmail();
        String password = AppUser.getPassword();
        String firstName = AppUser.getFirstName();
        String lastName = AppUser.getLastName();

        AppUser isEmailExist = AppUserRepository.findByEmail(email);
        if (isEmailExist != null) {
            //throw new Exception("Email Is Already Used With Another Account");

        }
        AppUser createdAppUser = new AppUser();
        createdAppUser.setEmail(email);
        createdAppUser.setFirstName(firstName);
        createdAppUser.setLastName(lastName);
        createdAppUser.setRole(model.Role.ROLE_USER);
        createdAppUser.setPassword(passwordEncoder.encode(password));

        AppUser savedAppUser = AppUserRepository.save(createdAppUser);
        AppUserRepository.save(createdAppUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);


        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody AppUser loginRequest) {
        String AppUsername = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(AppUsername + "-------" + password);

        Authentication authentication = authenticate(AppUsername, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }


    private Authentication authenticate(String AppUsername, String password) {

        System.out.println(AppUsername + "---++----" + password);

        AppUserDetailsService appUserDetailsService = new AppUserDetailsService(AppUserRepository);
        AppUserDetails AppUserDetails = appUserDetailsService.loadUserByUsername(AppUsername);
        UserDetails userDetails = appUserDetailsService.loadUserByUsername(AppUsername);

        System.out.println("Sig in in AppUser details" + AppUserDetails);

        if (AppUserDetails == null) {
            System.out.println("Sign in details - null" + AppUserDetails);

            throw new BadCredentialsException("Invalid AppUsername and password");
        }
        if (!passwordEncoder.matches(password, AppUserDetails.getPassword())) {
            System.out.println("Sign in AppUserDetails - password mismatch" + AppUserDetails);

            throw new BadCredentialsException("Invalid password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }


}