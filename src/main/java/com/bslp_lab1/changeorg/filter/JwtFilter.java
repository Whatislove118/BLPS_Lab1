package com.bslp_lab1.changeorg.filter;

import com.bslp_lab1.changeorg.beans.Message;
import com.bslp_lab1.changeorg.service.ChangeOrgUserDetails;
import com.bslp_lab1.changeorg.service.ChangeOrgUserDetailsService;
import com.bslp_lab1.changeorg.utils.JWTutils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    @Autowired
    private JWTutils JWTutils;
    @Autowired
    private ChangeOrgUserDetailsService changeOrgUserDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter logs: do filter");
        String token = JWTutils.getTokenFromRequest((HttpServletRequest) servletRequest);
        if(token != null && JWTutils.validateToken(token)){
            System.out.println("filter logs: token exists");
            String email = JWTutils.getEmailFromToken(token);
            ChangeOrgUserDetails changeOrgUserDetails = changeOrgUserDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(changeOrgUserDetails, null, null);
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("filter logs: auth completed");
        }else{
            String uri = ((HttpServletRequest) servletRequest).getRequestURI();
            System.out.println(uri);
            if(!uri.equals("/users/auth") && !uri.equals("/users/register")){
                ((HttpServletResponse)servletResponse).setStatus(401);
                ((HttpServletResponse)servletResponse).getOutputStream().write("Invalid token".getBytes());
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }



}
