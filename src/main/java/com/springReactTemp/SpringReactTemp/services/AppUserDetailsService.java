package com.springReactTemp.SpringReactTemp.services;

import com.springReactTemp.SpringReactTemp.repository.AppUserRepository;
import com.springReactTemp.SpringReactTemp.services.AppUserDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.springReactTemp.SpringReactTemp.model.AppUser;


public class AppUserDetailsService  implements UserDetailsService {
    private final AppUserRepository repo;

    public AppUserDetailsService(AppUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public AppUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repo.findByEmail(username);
        if (user == null) { System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new AppUserDetails(user);
    }
}
