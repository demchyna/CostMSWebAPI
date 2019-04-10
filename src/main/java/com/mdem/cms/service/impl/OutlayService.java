package com.mdem.cms.service.impl;

import com.mdem.cms.DAO.impl.OutlayDao;
import com.mdem.cms.DAO.impl.UserDao;
import com.mdem.cms.exception.DataNotFoundException;
import com.mdem.cms.model.Outlay;
import com.mdem.cms.model.User;
import com.mdem.cms.service.IOutlayService;
import com.mdem.cms.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OutlayService extends AbstractService<Outlay, Long> implements IOutlayService {

    private OutlayDao outlayDao;
    private UserDao userDao;

    @Autowired
    public void setOutlayDao(OutlayDao outlayDao) {
        this.outlayDao = outlayDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public List<Outlay> getOutlaysByUserId(long userId) {
        User user = userDao.getById(userId);
        if (user == null) {
            throw new DataNotFoundException("Record with id " + userId  +" not found in database");
        }
        return outlayDao.getOutlaysByUserId(userId);
    }
}
