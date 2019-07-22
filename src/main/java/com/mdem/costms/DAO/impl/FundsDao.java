package com.mdem.costms.DAO.impl;

import com.mdem.costms.DAO.IFundsDao;
import com.mdem.costms.DAO.common.AbstractDao;
import com.mdem.costms.model.Funds;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FundsDao extends AbstractDao<Funds, Long> implements IFundsDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Funds> getFundsByUserId(long userId) {
        Query query = getSession().createQuery("FROM " + Funds.class.getName() + " where user_id = :user_id");
        query.setParameter("user_id", userId);
        return (List<Funds>) query.getResultList();
    }
}
