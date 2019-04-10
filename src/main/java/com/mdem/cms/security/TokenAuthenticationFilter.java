package com.mdem.cms.security;

import com.mdem.cms.exception.BadTokenException;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@PropertySource("classpath:security.properties")
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${security.headerName}")
    private String HEADER_NAME;

    private Logger logger;

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public TokenAuthenticationFilter(String url) {
        super(new AntPathRequestMatcher(url));
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String token = request.getHeader(HEADER_NAME);

        if (token != null) {
            UserAuthentication userAuthentication = new UserAuthentication(token);
            try {
                return getAuthenticationManager().authenticate(userAuthentication);
            } catch (BadTokenException bte) {
                UserAuthentication.authenticationErrorResponse(request, response, bte);
                logger.error(bte.getMessage(), bte);
            }
        } else {
            throw new BadCredentialsException("Token is not found");
        }
        return null;
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.setHeader(HEADER_NAME, "Bearer " + TokenAuthenticationService.refreshToken(authentication));
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        SecurityContextHolder.clearContext();
        UserAuthentication.authenticationErrorResponse(request, response, exception);
        logger.error(exception.getMessage(), exception);
    }
}