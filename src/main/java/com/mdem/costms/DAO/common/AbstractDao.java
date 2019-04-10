package com.mdem.costms.DAO.common;

import com.mdem.costms.DAO.IAbstractDao;
import com.mdem.costms.model.common.IEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository
public abstract class AbstractDao<T extends IEntity, K extends Serializable> implements IAbstractDao<T, K> {

    private static SessionFactory sessionFactory;
    private Class<T> entityType;

    public AbstractDao() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityType = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Autowired
    private void setSessionFactory(SessionFactory sessionFactory) {
        AbstractDao.sessionFactory = sessionFactory;
    }

    protected static Session getSession() {
        return AbstractDao.sessionFactory.getCurrentSession();
    }

    @Override
    public void create(T entity) {
        getSession().save(entity);
    }

    @Override
    public T getById(K id) {
        return getSession().get(entityType, id);
    }

    @Override
    public void update(T entity) {
        getSession().merge(entity);
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    public List<T> getAll() {
        @SuppressWarnings("unchecked")
        List<T> entities = getSession().createQuery("FROM " + entityType.getName()).list();
        return entities;
    }
}
