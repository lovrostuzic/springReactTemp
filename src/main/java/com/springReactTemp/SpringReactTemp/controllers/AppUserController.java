package com.springReactTemp.SpringReactTemp.controllers;

import com.springReactTemp.SpringReactTemp.model.AppUser;
import com.springReactTemp.SpringReactTemp.repository.AppUserRepository;
import com.springReactTemp.SpringReactTemp.services.AppUserService;
import com.springReactTemp.SpringReactTemp.services.AppUserServiceImplementation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appUser")
public class AppUserController {
    @Autowired
    private com.springReactTemp.SpringReactTemp.repository.AppUserRepository AppUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppUserServiceImplementation customAppUserDetails;
    @Autowired
    private com.springReactTemp.SpringReactTemp.services.AppUserService AppUserService;

    @GetMapping("/all")
    public List<AppUser> allAppUsers() {
        return AppUserRepository.findAll();
    }

}
