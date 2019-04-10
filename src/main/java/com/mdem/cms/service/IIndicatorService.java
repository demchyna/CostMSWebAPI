package com.mdem.cms.service;

import com.mdem.cms.model.Indicator;

import java.util.List;

public interface IIndicatorService {
    List<Indicator> getIndicatorsByMeterId(long meterId);
    Indicator getLastAddedIndicatorByMeterId(long meterId);
}
