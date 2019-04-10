package com.mdem.costms.controller.common;

import com.mdem.costms.exception.IncorrectPasswordException;
import com.mdem.costms.model.User;
import com.mdem.costms.security.TokenAuthenticationService;
import com.mdem.costms.security.UserCredential;
import com.mdem.costms.service.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Api(tags = {"LogIn"}, description = "Operation for login registered users")
public class LoginController {
    @Value("${security.headerName}")
    private String HEADER_NAME;

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public LoginController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get an authentication token")
    public void getAuthenticationToken(@Validated @RequestBody UserCredential credential, HttpServletResponse response) throws IncorrectPasswordException {

        User user = (User)userService.loadUserByUsername(credential.getUsername());
        String fullToken;

        if (bCryptPasswordEncoder.matches(credential.getPassword(), user.getPassword())) {
            fullToken = TokenAuthenticationService.createToken(user);
            response.addHeader(HEADER_NAME, "Bearer " + fullToken);
        } else {
            throw new IncorrectPasswordException("Password is not correct");
        }
    }
}