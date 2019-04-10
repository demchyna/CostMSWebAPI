package com.mdem.cms.DAO;

import com.mdem.cms.model.common.IEntity;

import java.io.Serializable;
import java.util.List;

public interface IAbstractDao<T extends IEntity, K extends Serializable> {
    void create(T entity);
    T getById(K id);
    void update(T entity);
    void delete(T entity);
    List<T> getAll();
}