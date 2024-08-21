package com.springReactTemp.SpringReactTemp.services;
import com.springReactTemp.SpringReactTemp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import com.springReactTemp.SpringReactTemp.model.AppUser;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public List<AppUser> getAllAppUser(){
        return appUserRepository.findAll();
    }  ;

    public AppUser findUserByEmail(String email){
        return appUserRepository.findByEmail(email);
    } ;

    public AppUser findUserById(Long userId){
        return appUserRepository.findById(userId).get();
    } ;



    public AppUser postAppUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }


    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }
}
