package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.beans.Role;
import com.bslp_lab1.changeorg.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class ChangeOrgUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryService userRepositoryService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepositoryService.getUserRepService().findByEmail(email);
        return new ChangeOrgUserDetails(user);
    }


    private Collection<GrantedAuthority> getAuthorities(User user){
        Set<Role> userRoles = user.getRoles();
        Collection<GrantedAuthority> authorities = new ArrayList<>(userRoles.size());
        for(Role role : userRoles){
            System.out.println(role.getAuthority());
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return authorities;
    }



}
