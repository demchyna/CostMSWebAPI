package com.mdem.costms.DAO.impl;

import com.mdem.costms.DAO.ITariffDao;
import com.mdem.costms.DAO.common.AbstractDao;
import com.mdem.costms.model.Tariff;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TariffDao extends AbstractDao<Tariff, Long> implements ITariffDao {

    @Override
    public List<Tariff> getTariffsByCategoryId(long categoryId) {
        Query query = getSession().createQuery("FROM " + Tariff.class.getName() + " where category_id = :category_id");
        query.setParameter("category_id", categoryId);

        return (List<Tariff>) query.getResultList();
    }
}
