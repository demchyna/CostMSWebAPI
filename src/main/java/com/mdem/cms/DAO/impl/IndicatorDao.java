package com.mdem.cms.DAO.impl;

import com.mdem.cms.DAO.IIndicatorDao;
import com.mdem.cms.DAO.common.AbstractDao;
import com.mdem.cms.model.Indicator;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IndicatorDao extends AbstractDao<Indicator, Long> implements IIndicatorDao {

    @Override
    public List<Indicator> getIndicatorsByMeterId(long meterId) {
        Query query = getSession().createQuery("FROM " + Indicator.class.getName() + " WHERE meter_id = :meter_id ORDER BY date");
        query.setParameter("meter_id", meterId);
        return (List<Indicator>) query.getResultList();
    }

    @Override
    public Indicator getLastAddedIndicatorByMeterId(long meterId) {
        Query query = getSession().createQuery("FROM " + Indicator.class.getName() + " WHERE meter_id = :meter_id ORDER BY date DESC");
        query.setParameter("meter_id", meterId);
        query.setFirstResult(0);
        query.setMaxResults(1);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (Indicator) query.getResultList().get(0);
    }

}
