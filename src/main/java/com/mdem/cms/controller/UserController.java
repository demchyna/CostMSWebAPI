package com.mdem.cms.controller;

import com.mdem.cms.exception.NoDataException;
import com.mdem.cms.model.User;
import com.mdem.cms.service.impl.UserService;
import com.mdem.cms.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/user")
@Api(tags = {"User"}, description = "Operations for work with users")
public class UserController {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${user.password.pattern}")
    private String USER_PASSWORD_PATTERN;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #id == authentication.details.id)")
    @ApiOperation(value = "Search a user with an ID", response = User.class)
    public User getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        user.setPassword("");
        return user;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #user.id == authentication.details.id)")
    @ApiOperation(value = "Update an existing user")
    public void updateUser(@Validated @RequestBody User user, BindingResult result) throws MethodArgumentNotValidException {
        User oldUser = userService.getById(user.getId());
        if(user.getPassword().equals("")) {
            user.setPassword(oldUser.getPassword());
        } else {
            UserUtil.userValidate(user, result, USER_PASSWORD_PATTERN);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userService.update(user);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #user.id == authentication.details.id)")
    @ApiOperation(value = "Delete an existing user")
    public void deleteUser(@Validated @RequestBody User user) {
        userService.delete(user);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "View a list of available users", response = Iterable.class)
    public List<User> getAllUsers() {
        List<User> users = userService.getAll();
        users.forEach(user -> user.setPassword(""));
        return users;
    }

    @RequestMapping(value = "/login/{login}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Search a user with a login", response = User.class)
    public User getUserByLogin(@PathVariable String login) {
        User user = (User) userService.loadUserByUsername(login);
        user.setPassword("");
        return user;
    }

    @RequestMapping(value = "/credential", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #user.id == authentication.details.id)")
    @ApiOperation(value = "Check if user password is correct")
    public void checkPasswordByLogin(@RequestBody User user) {
        User oldUser = (User) userService.getById(user.getId());
        if (!bCryptPasswordEncoder.matches(user.getPassword(), oldUser.getPassword())) {
            throw new NoDataException("Password is not correct");
        }
    }

}