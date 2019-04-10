package com.mdem.cms.service.impl;

import com.mdem.cms.DAO.impl.IncomeDao;
import com.mdem.cms.DAO.impl.UserDao;
import com.mdem.cms.exception.DataNotFoundException;
import com.mdem.cms.model.Income;
import com.mdem.cms.model.User;
import com.mdem.cms.service.IIncomeService;
import com.mdem.cms.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IncomeService extends AbstractService<Income, Long> implements IIncomeService {

    private IncomeDao incomeDao;
    private UserDao userDao;

    @Autowired
    public void setIncomeDao(IncomeDao incomeDao) {
        this.incomeDao = incomeDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public List<Income> getIncomesByUserId(long userId) {
        User user = userDao.getById(userId);
        if (user == null) {
            throw new DataNotFoundException("Record with id " + userId  +" not found in database");
        }
        return incomeDao.getIncomesByUserId(userId);
    }
}
