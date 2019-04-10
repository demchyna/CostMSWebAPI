package com.mdem.cms.service.impl;

import com.mdem.cms.DAO.impl.CategoryDao;
import com.mdem.cms.DAO.impl.TariffDao;
import com.mdem.cms.exception.DataNotFoundException;
import com.mdem.cms.model.Category;
import com.mdem.cms.model.Tariff;
import com.mdem.cms.service.ITariffService;
import com.mdem.cms.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TariffService extends AbstractService<Tariff, Long> implements ITariffService {

    private TariffDao tariffDao;
    private CategoryDao categoryDao;

    @Autowired
    public void setTariffDao(TariffDao tariffDao) {
        this.tariffDao = tariffDao;
    }

    @Autowired
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    @Transactional
    public List<Tariff> getTariffsByCategoryId(long categoryId) {
        Category category = categoryDao.getById(categoryId);
        if (category == null) {
            throw new DataNotFoundException("Record with id " + categoryId  +" not found in database");
        }
        return tariffDao.getTariffsByCategoryId(categoryId);
    }
}
