package com.mdem.costms.service;

import com.mdem.costms.exception.ConflictDataException;
import com.mdem.costms.exception.DataNotFoundException;
import com.mdem.costms.exception.NoDataException;
import com.mdem.costms.model.common.IEntity;

import java.io.Serializable;
import java.util.List;

public interface IAbstractService<T extends IEntity, K extends Serializable> {
    void create(T entity) throws ConflictDataException;
    T getById(K id) throws DataNotFoundException;
    void update(T entity) throws DataNotFoundException;
    void delete(T entity) throws DataNotFoundException;
    List<T> getAll() throws NoDataException;
}
