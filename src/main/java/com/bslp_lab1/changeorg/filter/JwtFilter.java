package com.bslp_lab1.changeorg.filter;

import com.bslp_lab1.changeorg.service.ChangeOrgUserDetails;
import com.bslp_lab1.changeorg.service.ChangeOrgUserDetailsService;
import com.bslp_lab1.changeorg.utils.JWTutils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/petition/add","/petition/subscribe"})
public class JwtFilter implements Filter{

    @Autowired
    private JWTutils JWTutils;
    @Autowired
    private ChangeOrgUserDetailsService changeOrgUserDetailsService;
    Logger logger = LogManager.getLogger(JwtFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.log(Level.INFO, "Filter logs: do filter");
        String token = JWTutils.getTokenFromRequest((HttpServletRequest) servletRequest);
        if(token != null && JWTutils.validateToken(token)){
            logger.log(Level.INFO, "Filter logs: token exists");
            String email = JWTutils.getEmailFromToken(token);
            try{
                ChangeOrgUserDetails changeOrgUserDetails = changeOrgUserDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(changeOrgUserDetails, null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.log(Level.INFO, "Filter logs: auth completed");
            }catch (NullPointerException e){
                logger.log(Level.INFO, "Filter logs: auth failed");
                ((HttpServletResponse)servletResponse).setStatus(401);
                ((HttpServletResponse)servletResponse).sendError(401, "Authorization failed. Invalid token");
            }
          }
        filterChain.doFilter(servletRequest, servletResponse);

    }



}
