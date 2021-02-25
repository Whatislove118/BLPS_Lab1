package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.beans.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class ChangeOrgUserDetails implements UserDetails {

    private String email;
    private String password;

    public static ChangeOrgUserDetails fromUserToUserDetailsService(User user){
        ChangeOrgUserDetails changeOrgUserDetails = new ChangeOrgUserDetails();
        changeOrgUserDetails.email = user.getEmail();
        changeOrgUserDetails.password = user.getPassword();
        return changeOrgUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
