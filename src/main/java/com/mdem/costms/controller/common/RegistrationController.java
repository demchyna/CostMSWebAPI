package com.mdem.costms.controller.common;

import com.mdem.costms.model.User;
import com.mdem.costms.service.impl.RoleService;
import com.mdem.costms.service.impl.UserService;
import com.mdem.costms.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@SuppressWarnings("deprecation")
@Api(tags = {"Registration"}, description = "Operation for users registration")
@PropertySource("classpath:messages.properties")
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${user.password.pattern}")
    private String USER_PASSWORD_PATTERN;

    @Autowired
    public RegistrationController(UserService userService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Registration a new user")
    public void createUser(@Validated @RequestBody User user, BindingResult result) throws MethodArgumentNotValidException {
        UserUtil.userValidate(user, result, USER_PASSWORD_PATTERN);
        user.setAuthorities(Collections.singletonList(roleService.getRoleByName("user")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.create(user);
    }
}
