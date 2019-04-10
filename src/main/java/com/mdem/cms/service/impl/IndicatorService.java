package com.mdem.cms.service.impl;

import com.mdem.cms.DAO.impl.IndicatorDao;
import com.mdem.cms.DAO.impl.MeterDao;
import com.mdem.cms.exception.DataNotFoundException;
import com.mdem.cms.model.Indicator;
import com.mdem.cms.model.Meter;
import com.mdem.cms.service.IIndicatorService;
import com.mdem.cms.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IndicatorService extends AbstractService<Indicator, Long> implements IIndicatorService {

    private IndicatorDao indicatorDao;
    private MeterDao meterDao;

    @Autowired
    public void setIndicatorDao(IndicatorDao indicatorDao) {
        this.indicatorDao = indicatorDao;
    }

    @Autowired
    public void setMeterDao(MeterDao meterDao) {
        this.meterDao = meterDao;
    }

    @Override
    @Transactional
    public List<Indicator> getIndicatorsByMeterId(long meterId) {
        Meter meter = meterDao.getById(meterId);
        if (meter == null) {
            throw new DataNotFoundException("Record with id " + meterId  +" not found in database");
        }
        return indicatorDao.getIndicatorsByMeterId(meterId);
    }

    @Override
    @Transactional
    public Indicator getLastAddedIndicatorByMeterId(long meterId) {
        Meter meter = meterDao.getById(meterId);
        if (meter == null) {
            throw new DataNotFoundException("Record with id " + meterId  +" not found in database");
        }
        return indicatorDao.getLastAddedIndicatorByMeterId(meterId);
    }
}
