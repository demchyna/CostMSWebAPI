package com.mdem.costms.service;

import com.mdem.costms.model.Indicator;

import java.util.List;

public interface IIndicatorService {
    List<Indicator> getIndicatorsByMeterId(long meterId);
    Indicator getLastAddedIndicatorByMeterId(long meterId);
}
