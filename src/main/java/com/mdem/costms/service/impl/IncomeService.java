package com.mdem.costms.service.impl;

import com.mdem.costms.DAO.impl.IncomeDao;
import com.mdem.costms.DAO.impl.UserDao;
import com.mdem.costms.exception.DataNotFoundException;
import com.mdem.costms.model.Income;
import com.mdem.costms.model.User;
import com.mdem.costms.service.IIncomeService;
import com.mdem.costms.service.common.AbstractService;
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
