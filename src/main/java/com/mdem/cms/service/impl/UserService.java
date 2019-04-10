package com.mdem.cms.service.impl;

import com.mdem.cms.DAO.impl.UserDao;
import com.mdem.cms.exception.UserNotFoundException;
import com.mdem.cms.model.User;
import com.mdem.cms.service.IUserService;
import com.mdem.cms.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService extends AbstractService<User, Long> implements IUserService, UserDetailsService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.getUserByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("User with login " + username  +" not found in database");
        }
    }
}