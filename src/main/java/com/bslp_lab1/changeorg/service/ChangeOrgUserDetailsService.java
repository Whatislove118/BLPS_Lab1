package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ChangeOrgUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryService userRepositoryService;


    @Override
    public ChangeOrgUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepositoryService.getUserRepService().findByEmail(email);
        return ChangeOrgUserDetails.fromUserToUserDetailsService(user);
    }
}
