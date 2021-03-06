package com.mdem.costms.DAO;

import com.mdem.costms.model.Indicator;

import java.util.List;

public interface IIndicatorDao {
    List<Indicator> getIndicatorsByMeterId(long meterId);
    Indicator getLastAddedIndicatorByMeterId(long meterId);
}
