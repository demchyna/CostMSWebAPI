package com.mdem.cms.DAO.impl;

import com.mdem.cms.DAO.IIncomeDao;
import com.mdem.cms.DAO.common.AbstractDao;
import com.mdem.cms.model.Income;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IncomeDao extends AbstractDao<Income, Long> implements IIncomeDao {

    @Override
    public List<Income> getIncomesByUserId(long userId) {
        Query query = getSession().createQuery("FROM " + Income.class.getName() + " where user_id = :user_id");
        query.setParameter("user_id", userId);

        return (List<Income>) query.getResultList();
    }
}
