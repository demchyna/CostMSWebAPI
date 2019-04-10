package com.mdem.costms.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdem.costms.model.common.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class UserAuthentication implements Authentication {

    private String token;
    private UserDetails userDetails;
    private boolean authenticated;

    public UserAuthentication(String token) {
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    public void setPrincipal(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }

    public String getToken() {
        return token;
    }


    public static void authenticationErrorResponse(HttpServletRequest request, HttpServletResponse response, RuntimeException exception) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        String errorURL = request.getRequestURL().toString();
        String errorMessage = exception.getMessage();
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.FORBIDDEN.value(), errorURL, errorMessage);

        PrintWriter out = response.getWriter();
        String jsonString = new ObjectMapper().writeValueAsString(errorInfo);

        out.print(jsonString);
        out.flush();
    }
}
