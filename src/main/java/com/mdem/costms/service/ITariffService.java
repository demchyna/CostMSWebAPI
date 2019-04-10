package com.mdem.costms.service;

import com.mdem.costms.model.Tariff;

import java.util.List;

public interface ITariffService {
    List<Tariff> getTariffsByCategoryId(long categoryId);
}
