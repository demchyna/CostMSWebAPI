package com.mdem.cms.DAO;

import com.mdem.cms.model.Indicator;

import java.util.List;

public interface IIndicatorDao {
    List<Indicator> getIndicatorsByMeterId(long meterId);
    Indicator getLastAddedIndicatorByMeterId(long meterId);
}
