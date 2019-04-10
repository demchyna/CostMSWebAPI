package com.mdem.cms.DAO;

import com.mdem.cms.model.User;

public interface IUserDao {
    User getUserByUsername(String login);
}
