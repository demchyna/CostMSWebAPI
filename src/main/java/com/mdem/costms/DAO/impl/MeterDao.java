package com.mdem.costms.DAO.impl;

import com.mdem.costms.DAO.IMeterDao;
import com.mdem.costms.DAO.common.AbstractDao;
import com.mdem.costms.model.Meter;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MeterDao extends AbstractDao<Meter, Long> implements IMeterDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Meter> getMetersByCategoryId(long categoryId) {
        Query query = getSession().createQuery("FROM " + Meter.class.getName() + " where category_id = :category_id");
        query.setParameter("category_id", categoryId);
        return (List<Meter>) query.getResultList();
    }
}
