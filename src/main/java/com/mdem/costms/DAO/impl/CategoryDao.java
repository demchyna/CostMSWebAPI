package com.mdem.costms.DAO.impl;

import com.mdem.costms.DAO.ICategoryDao;
import com.mdem.costms.DAO.common.AbstractDao;
import com.mdem.costms.model.Category;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDao extends AbstractDao<Category, Long> implements ICategoryDao {
    @Override
    public List<Category> getCategoriesByUserId(long userId) {
        Query query = getSession().createQuery("FROM " + Category.class.getName() + " where user_id = :user_id");
        query.setParameter("user_id", userId);

        return (List<Category>) query.getResultList();
    }
}
