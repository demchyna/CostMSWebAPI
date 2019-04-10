package com.mdem.costms.controller;

import com.mdem.costms.model.Role;
import com.mdem.costms.model.User;
import com.mdem.costms.security.TokenAuthenticationService;
import com.mdem.costms.service.IAbstractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController("roleController")
@RequestMapping("api/role")
@Api(tags = {"Role"}, description = "Operations for work with user roles")
public class RoleController {

    private IAbstractService<Role, Long> roleService;

    @Autowired
    public RoleController(IAbstractService<Role, Long> roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Add a new role")
    public void createRole(@Validated @RequestBody Role role) {
        roleService.create(role);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and @roleController.belongCurrentUser(#id, #request))")
    @ApiOperation(value = "Search a role with an ID", response = Role.class)
    public Role getRoleById(@PathVariable Long id, @ApiIgnore HttpServletRequest request) {
        return roleService.getById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update an existing role")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateRole(@Validated @RequestBody Role role) {
        roleService.update(role);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete an existing role")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRole(@Validated @RequestBody Role role) {
        roleService.delete(role);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "View a list of available roles", response = Iterable.class)
    public List<Role> getAllRoles() {
        return roleService.getAll();
    }

    public boolean belongCurrentUser(Long roleId, HttpServletRequest request) {
        List<User> users = roleService.getById(roleId).getUsers();
        for (User user : users) {
            if (user.getId().equals(TokenAuthenticationService.getUserIdFromToken(request.getHeader("Authorization")))) {
                return true;
            }
        }
        return false;
    }
}