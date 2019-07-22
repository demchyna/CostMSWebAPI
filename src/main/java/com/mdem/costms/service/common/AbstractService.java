package com.mdem.costms.service.common;

import com.mdem.costms.DAO.IAbstractDao;
import com.mdem.costms.exception.ConflictDataException;
import com.mdem.costms.exception.DataNotFoundException;
import com.mdem.costms.exception.NoDataException;
import com.mdem.costms.model.common.IEntity;
import com.mdem.costms.service.IAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Service
public abstract class AbstractService<T extends IEntity, K extends Serializable> implements IAbstractService<T, K> {

    private IAbstractDao<T, K> abstractDao;
    private final Class<T> entityType;

    @Autowired
    public void setAbstractDao(IAbstractDao<T, K> abstractDao) {
        this.abstractDao = abstractDao;
    }

    @SuppressWarnings("unchecked")
    public AbstractService() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityType = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public void create(T entity) throws ConflictDataException {
        if (entity.getId() != null) {
            T extEntity = abstractDao.getById((K) entity.getId());
            if (extEntity == null) {
                abstractDao.create(entity);
            } else {
                throw new ConflictDataException("Record already exists in database");
            }
        } else {
            abstractDao.create(entity);
        }
    }

    @Override
    @Transactional
    public T getById(K id) throws DataNotFoundException {
        T entity = abstractDao.getById(id);
        if (entity != null) {
            return entity;
        } else {
            throw new DataNotFoundException("Record with id " + id  +" not found in database");
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public void update(T entity) throws DataNotFoundException {
        T updEntity = abstractDao.getById((K) entity.getId());
        if (updEntity != null) {
            abstractDao.update(entity);
        } else {
            throw new DataNotFoundException("Record with id " + entity.getId()  +" not found in database");
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public void delete(T entity) throws DataNotFoundException {
        T delEntity = abstractDao.getById((K) entity.getId());
        if (delEntity != null) {
            abstractDao.delete(delEntity);
        } else {
            throw new DataNotFoundException("Record with id " + entity.getId()  +" not found in database");
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List getAll() throws NoDataException {
        List<T> categories = abstractDao.getAll();
        if (!categories.isEmpty()) {
            return categories;
        } else {
            throw new NoDataException("Table '" + entityType.getSimpleName().toLowerCase() + "' in database is empty");
        }
    }
}
