package com.mdem.costms.DAO;

import com.mdem.costms.model.User;

public interface IUserDao {
    User getUserByUsername(String login);
}
