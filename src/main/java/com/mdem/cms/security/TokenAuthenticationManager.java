package com.mdem.cms.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdem.cms.exception.BadTokenException;
import com.mdem.cms.model.Role;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TokenAuthenticationManager implements AuthenticationManager {

    private Logger logger;

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws BadTokenException {

        UserAuthentication userAuthentication = (UserAuthentication) authentication;

        try {
            long userId = TokenAuthenticationService.getUserIdFromToken(userAuthentication.getToken());
            String username = TokenAuthenticationService.getUsernameFromToken(userAuthentication.getToken());
            List<Role> authorities = new ObjectMapper().convertValue(
                    TokenAuthenticationService.getRolesFromToken(userAuthentication.getToken()),
                    new TypeReference<List<Role>>() { }
            );

            UserProxy userProxy = new UserProxy(userId, username, authorities);
            userAuthentication.setPrincipal(userProxy);
            userAuthentication.setAuthenticated(true);

        } catch (SignatureException se) {
            logger.error("Token is not valid", se);
            throw new BadTokenException("Token is not valid");


        } catch (ExpiredJwtException ee) {
            logger.error("Token must be refreshed", ee);
            throw new BadTokenException("Token must be refreshed");
        }

        return userAuthentication;
    }
}