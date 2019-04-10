package com.mdem.cms.service;

import com.mdem.cms.model.Tariff;

import java.util.List;

public interface ITariffService {
    List<Tariff> getTariffsByCategoryId(long categoryId);
}
