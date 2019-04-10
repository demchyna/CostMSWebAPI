package com.mdem.costms.DAO.impl;

import com.mdem.costms.DAO.IOutlayDao;
import com.mdem.costms.DAO.common.AbstractDao;
import com.mdem.costms.model.Outlay;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OutlayDao extends AbstractDao<Outlay, Long> implements IOutlayDao {
    @Override
    public List<Outlay> getOutlaysByUserId(long userId) {
        Query query = getSession().createQuery("FROM " + Outlay.class.getName() + " where user_id = :user_id");
        query.setParameter("user_id", userId);

        return (List<Outlay>) query.getResultList();
    }
}
